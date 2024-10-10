package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final ICouponRepository couponRepository;


    @Override
    public Order orderNow(OrderRequest orderRequest) {
        Users user = userService.getCurrentLoggedInUser();

        List<ShoppingCart> shoppingCartsItems = cartRepository.findAllByUsers(user);
        if (shoppingCartsItems.isEmpty()) {
            throw new NoSuchElementException("Giỏ hàng trống, không có sản phẩm nào để đặt hàng");
        }
        if (
                orderRequest.getAddressId() == null
                        && orderRequest.getReceiveFullAddress() == null
                        && orderRequest.getReceiveName() == null
                        && orderRequest.getReceivePhone() == null
        )
        {
            throw new NoSuchElementException("Vui lòng nhập đầy đủ thông tin");
        }

        Address address = null;
        if (orderRequest.getAddressId() != null) {
            address = addressRepository.findById(orderRequest.getAddressId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy địa chỉ cho người dùng. Vui lòng thêm địa chỉ trước khi đặt hàng."));
        }
       if(address!= null && address.getUsers().getId() != user.getId()) {
            throw new NoSuchElementException("Địa chỉ không phải của bạn! vui lòng nhập lại");
        }
        Coupon coupon = null;

        if (orderRequest.getCouponId() != null) {
            coupon = couponRepository.findById(orderRequest.getCouponId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy phiếu giảm giá . Vui lòng nhập lại mã"));
            if (coupon.getStock() <= 0) {
                throw new NoSuchElementException("Coupon đã hết, vui lòng sử dụng coupon khác");
            }
            Date currentDate = new Date();
            if (coupon.getStardate() != null && currentDate.before(coupon.getStardate())) {
                throw new NoSuchElementException("Coupon chưa đến ngày sử dụng");
            }
            if (coupon.getEnddate() != null && currentDate.after(coupon.getEnddate())) {
                throw new NoSuchElementException("Coupon đã hết hạn");
            }
        }

        double total = shoppingCartsItems.stream()
                .map(item -> item.getOrderQuantity() * item.getProductDetail().getUnitPrice())
                .reduce(0.0, Double::sum);

        String receiveName, receiveFullAddress, receivePhone;

        if (orderRequest.getAddressId() != null) {
            receiveName = address.getReceiveName();
            receiveFullAddress = address.getFullAddress();
            receivePhone = address.getPhone();
        } else {
            if (orderRequest.getReceiveName() == null || orderRequest.getReceiveName().isEmpty()) {
                throw new NoSuchElementException("Tên người nhận không được để trống");
            }
            receiveName = orderRequest.getReceiveName();

            if (orderRequest.getReceiveFullAddress() == null || orderRequest.getReceiveFullAddress().isEmpty()) {
                throw new NoSuchElementException("Địa chỉ nhận không được để trống");
            }
            receiveFullAddress = orderRequest.getReceiveFullAddress();

            if (orderRequest.getReceivePhone() == null || orderRequest.getReceivePhone().isEmpty()) {
                throw new NoSuchElementException("Số điện thoại không được để trống");
            }
            receivePhone = orderRequest.getReceivePhone();
        }

        Order order = Order.builder()
                .users(user)
                .serialNumber(UUID.randomUUID().toString())
                .totalPrice(coupon != null ? (total - (total * coupon.getPercent() / 100)) : total)
                .status(OrderStatus.WAITING)
//                .receiveName(orderRequest.getAddressId() != null ? address.getReceiveName() : orderRequest.getReceiveName())
//                .receiveFullAddress(orderRequest.getAddressId() != null ? address.getFullAddress() : orderRequest.getReceiveFullAddress())
//                .receivePhone(orderRequest.getAddressId() != null ? address.getPhone() : orderRequest.getReceivePhone())
                .receiveName(receiveName)
                .receiveFullAddress(receiveFullAddress)
                .receivePhone(receivePhone)
                .note(orderRequest.getNote())
                .coupon(coupon)
                .createdAt(new Date())
                .receivedAt(new Date(new Date().getTime() + 4 * 24 * 60 * 60 * 1000))
                .build();
        order = orderRepository.save(order);

        List<OrderDetails> list = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCartsItems) {
            ProductDetail productDetail = shoppingCart.getProductDetail();
            if (productDetail.getStockQuantity() < shoppingCart.getOrderQuantity()) {
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
        if(coupon != null) {
            coupon.setStock(coupon.getStock() - 1);
            couponRepository.save(coupon);
        }
        return order;
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable, String search) {
        Page<Order> orderPage;
        if(search.isEmpty()){
            orderPage = orderRepository.findAll(pageable);
        }else {
            orderPage = orderRepository.findAllByUsersUsernameContainsIgnoreCase(search,pageable);
        }

        if (orderPage.isEmpty()) {
            throw new SuccessException("Không tìm thấy tên người dùng");
        }

        return orderPage;
    }

    @Override
    public Page<OrderResponse> getOrderResponsesByStatus(OrderStatus status, Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findByStatus(status, pageable);

        if (ordersPage.isEmpty()) {
            throw new NoSuchElementException("Không có đơn hàng nào trong trạng thái: " + status);
        }

        // Chuyển đổi từ Page<Order> sang Page<OrderResponse>
        return ordersPage.map(order -> OrderResponse.builder()
                .id(order.getId())
                .username(order.getUsers().getUsername())
                .userId(order.getUsers().getId())
                .serialNumber(order.getSerialNumber())
                .totalPrice(order.getTotalPrice())
                .receiveName(order.getReceiveName())
                .receivePhone(order.getReceivePhone())
                .receiveAddress(order.getReceiveFullAddress())
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
                .build());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại đơn hàng"));
        List<Product> products = order.getOrderDetails().stream()
                .map(detail -> {
                    Product product = detail.getProductDetail().getProduct();
                    return Product.builder()
                            .id(product.getId())
                            .productName(product.getProductName())
                            .image(product.getImage())
                            .build();
                })
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .username(order.getUsers().getUsername())
                .userId(order.getUsers().getId())
                .serialNumber(order.getSerialNumber())
                .totalPrice(order.getTotalPrice())
                .receiveName(order.getReceiveName())
                .receivePhone(order.getReceivePhone())
                .receiveAddress(order.getReceiveFullAddress())
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
                .product(products)
                .build();
    }

    @Override
    public boolean updateOrderStatus(Long id, OrderStatus status) throws CustomException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại đơn hàng"));

        OrderStatus currentStatus = order.getStatus();

        if (order.getStatus() == OrderStatus.CANCEL) {
            throw new CustomException("Không thể thay đổi trạng thái đơn hàng vì người dùng đã hủy đơn hàng", HttpStatus.BAD_REQUEST);
        }

        if (order.getStatus() == OrderStatus.SUCCESS) {
            throw new CustomException("Đơn hàng đã giao thành công nên khôn thể thay đổi trạng thái", HttpStatus.BAD_REQUEST);
        }

        if (!canChaneStatus(currentStatus, status)) {
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
    public void getAllUserOrders() {
//        Users user = userService.getCurrentLoggedInUser();
//        List<Order> orders = orderRepository.findAllByUsers(user);
//        if (orders.isEmpty()) {
//            throw new IllegalArgumentException("Không có đơn hàng nào cho người dùng này.");
//        }
        return ;
    }

    @Override
    public Order getOrderBySerialNumber(String serialNumber) {
        Users user = userService.getCurrentLoggedInUser();
        return orderRepository.findBySerialNumberAndUsers(serialNumber, user)
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại đơn hàng với mã này"));
    }

    @Override
    public Page<Order> getOrdersByStatus(OrderStatus orderStatus, Pageable pageable) {
        Users user = userService.getCurrentLoggedInUser();
        Page<Order> orders = orderRepository.findByStatusAndUsers(orderStatus, user, pageable);
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
    public Page<TopSellingProductResponse> getTopSellingProducts(Integer limit, Pageable pageable) throws CustomException {
        // Giới hạn số lượng sản phẩm bán chạy tối đa
        if (limit < 1) {
            throw new CustomException("Limit phải lớn hơn 0", HttpStatus.BAD_REQUEST);
        }

        // Lấy kết quả từ repository
        Page<Object[]> results = orderDetailRepository.findTopSellingProducts(pageable);

        if (results.isEmpty()) {
            throw new CustomException("Không có sản phẩm bán chạy nào", HttpStatus.BAD_REQUEST);
        }

        List<TopSellingProductResponse> topSellingProducts = results.stream().map(result -> {
            Product product = (Product) result[0];
            Long purchaseCount = (Long) result[1];

            TopSellingProductResponse response = new TopSellingProductResponse();
            response.setProduct(product);
            response.setPurchaseCount(purchaseCount);
            return response;
        }).collect(Collectors.toList());

        // Trả về Page
        return new PageImpl<>(topSellingProducts, pageable, results.getTotalElements());
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Double totalRevenue() {

        return orderRepository.sumTotalPrice();
    }

    @Override
    public Page<Order> getUserOrdersWithPaginationAndSearch(Pageable pageable, String search) {
        Users user = userService.getCurrentLoggedInUser();
        Page<Order> orders;

        if (search == null || search.trim().isEmpty()) {
            orders = orderRepository.findAllByUsers(user, pageable);
        } else {
            // Chuyển đổi search sang OrderStatus
            OrderStatus status;
            try {
                status = OrderStatus.valueOf(search.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new NoSuchElementException("Trạng thái không hợp lệ.");
            }

            orders = orderRepository.findAllByUsersAndStatus(user, status, pageable);
        }

        if (orders.isEmpty()) {
            throw new NoSuchElementException("Không có đơn hàng nào cho người dùng này.");
        }
        return orders;

}}

