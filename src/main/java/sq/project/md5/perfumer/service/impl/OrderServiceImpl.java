package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.advice.SuccessException;
import sq.project.md5.perfumer.constants.OrderStatus;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.OrderRequest;
import sq.project.md5.perfumer.model.dto.resp.OrderDetailResponse;
import sq.project.md5.perfumer.model.dto.resp.OrderResponse;
import sq.project.md5.perfumer.model.dto.resp.TopSellingProductResponse;
import sq.project.md5.perfumer.model.entity.*;
import sq.project.md5.perfumer.repository.*;
import sq.project.md5.perfumer.service.IAddressService;
import sq.project.md5.perfumer.service.IOrderService;
import sq.project.md5.perfumer.service.IUserService;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;

    private final ICartRepository cartRepository;

    private final IProductRepository productRepository;

    private final IProductDetailRepository productDetailRepository;

    private final IUserService userService;

    private final IOrderDetailRepository orderDetailRepository;

    private final IAddressService addressService;

    private final IAddressRepository addressRepository;



    @Override
    public Order orderNow(OrderRequest orderRequest) {
        Users user = userService.getCurrentLoggedInUser();

        List<ShoppingCart> shoppingCartsItems = cartRepository.findAllByUsers(user);
        if(shoppingCartsItems.isEmpty()) {
            throw new NoSuchElementException("Giỏ hàng trống, không có sản phẩm nào để đặt hàng");
        }

        Address address = addressService.getDefaultAddressForUser(user);

        if (address == null) {
            throw new NoSuchElementException("Không tìm thấy địa chỉ cho người dùng. Vui lòng thêm địa chỉ trước khi đặt hàng.");
        }
        Order order = Order.builder()
                .users(user)
                .serialNumber(UUID.randomUUID().toString())
                .totalPrice(calculateTotalPrice(shoppingCartsItems))
                .status(OrderStatus.WAITING)
                .receiveAddressId(address.getId())
                .note(orderRequest.getNote())
                .createdAt(new Date())
                .receivedAt(addDays(new Date(), 4))
                .build();
        order = orderRepository.save(order);

        List<OrderDetails> list = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCartsItems) {
            ProductDetail productDetail = shoppingCart.getProductDetail();
            if(productDetail.getStockQuantity()<shoppingCart.getOrderQuantity()){
                throw new NoSuchElementException("Số lượng đặt hàng vượt quá số lượng sản phẩm");
            }
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setProductDetail(shoppingCart.getProductDetail());
            orderDetails.setName(shoppingCart.getProductDetail().getProduct().getProductName());
            orderDetails.setUnitPrice(shoppingCart.getProductDetail().getUnitPrice());
            orderDetails.setOrderQuantity(shoppingCart.getOrderQuantity());
            list.add(orderDetails);

            productDetail.setStockQuantity(productDetail.getStockQuantity() - shoppingCart.getOrderQuantity());
            productDetailRepository.save(productDetail);
        }
        List<OrderDetails> orderDetails = orderDetailRepository.saveAll(list);
        order.setOrderDetails(orderDetails);
        cartRepository.deleteAll(shoppingCartsItems);
        return order;
    }

    private Double calculateTotalPrice(List<ShoppingCart> shoppingCartItems) {
        return shoppingCartItems.stream().mapToDouble(item-> {
                    ProductDetail productDetail = item.getProductDetail();
                    return productDetail.getUnitPrice() * item.getOrderQuantity();
                })
                .sum();
    }

    private Date addDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()) {
            throw new SuccessException("Không có sản phẩm nào trong đơn hàng");
        }

        return orders;
    }

    @Override
    public List<OrderResponse> getOrderResponsesByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);

        if(orders.isEmpty()) {
            throw new NoSuchElementException("Không có đơn hàng nào trong trạng thái: " + status);
        }

        return orders.stream().map(order -> OrderResponse.builder()
                .id(order.getId())
                .username(order.getUsers().getUsername())
                .userId(order.getUsers().getId())
                .serialNumber(order.getSerialNumber())
                .totalPrice(order.getTotalPrice())
                .receiveAddressId(order.getReceiveAddressId())
                .note(order.getNote())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .receivedAt(order.getReceivedAt())
                .orderDetail(order.getOrderDetails().stream().map(detail ->
                                OrderDetailResponse.builder()
                                        .productDetailId(detail.getProductDetail().getId())
                                        .name(detail.getName())
                                        .unitPrice(detail.getUnitPrice())
                                        .quantity(detail.getOrderQuantity())
                                        .build())
                        .collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order =  orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại đơn hàng"));

        return OrderResponse.builder()
                .id(order.getId())
                .username(order.getUsers().getUsername())
                .userId(order.getUsers().getId())
                .serialNumber(order.getSerialNumber())
                .totalPrice(order.getTotalPrice())
                .receiveAddressId(order.getReceiveAddressId())
                .note(order.getNote())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .receivedAt(order.getReceivedAt())
                .orderDetail(
                        order.getOrderDetails().stream().map(detail ->
                                        OrderDetailResponse.builder()
                                                .productDetailId(detail.getProductDetail().getId())
                                                .name(detail.getName())
                                                .unitPrice(detail.getUnitPrice())
                                                .quantity(detail.getOrderQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean updateOrderStatus(Long id, OrderStatus status) throws CustomException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại đơn hàng"));

        OrderStatus currentStatus = order.getStatus();

        if(order.getStatus() == OrderStatus.CANCEL) {
            throw new CustomException("Không thể thay đổi trạng thái đơn hàng vì người dùng đã hủy đơn hàng", HttpStatus.BAD_REQUEST);
        }

        if(order.getStatus() == OrderStatus.SUCCESS) {
            throw new CustomException("Đơn hàng đã giao thành công nên khôn thể thay đổi trạng thái", HttpStatus.BAD_REQUEST);
        }

        if(!canChaneStatus(currentStatus, status)){
            throw new CustomException("Không thể thay dổi trang thái của đơn hàng trước đó", HttpStatus.BAD_REQUEST);
        }
        order.setStatus(status);
        orderRepository.save(order);
        return true;
    }

    private boolean canChaneStatus(OrderStatus currentStatus, OrderStatus newStatus) {
        return currentStatus.ordinal() < newStatus.ordinal();
    }


    @Override
    public List<Order> getAllUserOrders() {
        Users user = userService.getCurrentLoggedInUser();
        List<Order> orders = orderRepository.findAllByUsers(user);
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("Không có đơn hàng nào cho người dùng này.");
        }
        return orders;
    }

    @Override
    public Order getOrderBySerialNumber(String serialNumber) {
        Users user = userService.getCurrentLoggedInUser();
        return orderRepository.findBySerialNumberAndUsers(serialNumber, user)
                .orElseThrow(() ->new NoSuchElementException("Không tồn tại đơn hàng với mã này"));
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        Users user = userService.getCurrentLoggedInUser();
        List<Order> orders = orderRepository.findByStatusAndUsers(orderStatus, user);
        if (orders.isEmpty()) {
            throw new NoSuchElementException("Không tìm thấy đơn hàng nào với trạng thái: " + orderStatus);
        }
        return orders;
    }


    @Override
    public boolean cancelOrder(Long id) {
        // Lấy người dùng hiện tại
        Users user = userService.getCurrentLoggedInUser();

        // Tìm đơn hàng theo ID và người dùng
        Order order = orderRepository.findByIdAndUsers(id, user)
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại đơn hàng hoặc bạn không có quyền truy cập"));

        // Kiểm tra trạng thái đơn hàng
        if (order.getStatus() == OrderStatus.WAITING) {
            // Cập nhật số lượng sản phẩm trong kho
            for (OrderDetails orderDetail : order.getOrderDetails()) {
                ProductDetail productDetail = orderDetail.getProductDetail();
                productDetail.setStockQuantity(productDetail.getStockQuantity() + orderDetail.getOrderQuantity());
                productDetailRepository.save(productDetail);
            }

            // Thay đổi trạng thái đơn hàng thành CANCEL
            order.setStatus(OrderStatus.CANCEL);
            orderRepository.save(order);
            return true;
        } else if (order.getStatus() == OrderStatus.CONFIRM) {
            throw new IllegalArgumentException("Sản phẩm đã được xác nhận, bạn không thể hủy đơn hàng");
        }

        return false; // Trả về false nếu không có điều kiện nào được thỏa mãn
    }

    @Override
    public List<TopSellingProductResponse> getTopSellingProducts(Integer limit) throws CustomException {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = orderDetailRepository.findTopSellingProducts(pageable);

        if (results.isEmpty()) {
            throw new CustomException("Không có sản phẩm bán chạy nào", HttpStatus.BAD_REQUEST);
        }

        List<TopSellingProductResponse> topSellingProducts = new ArrayList<>();
        for (Object[] result : results) {
            Product product = (Product) result[0];
            Long purchaseCount = (Long) result[1];

            TopSellingProductResponse response = new TopSellingProductResponse();
            response.setProduct(product);
            response.setPurchaseCount(purchaseCount);

            topSellingProducts.add(response);
        }
        return topSellingProducts;
    }


}

