package sq.project.md5.perfumer.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.constants.OrderStatus;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.OrderRequest;
import sq.project.md5.perfumer.model.dto.resp.OrderResponse;
import sq.project.md5.perfumer.model.dto.resp.TopSellingProductResponse;
import sq.project.md5.perfumer.model.entity.Order;

import java.util.List;

public interface IOrderService {
    Order orderNow(OrderRequest orderRequest) ;
    Page<Order> getAllOrders(Pageable pageable, String search);
    Page<OrderResponse> getOrderResponsesByStatus(OrderStatus status, Pageable pageable);
    OrderResponse getOrderById(Long id);
    boolean updateOrderStatus(Long id, OrderStatus status) throws CustomException;
    void getAllUserOrders();
    Order getOrderBySerialNumber(String serialNumber);
    Page<Order> getOrdersByStatus(OrderStatus orderStatus, Pageable pageable);
    boolean cancelOrder(Long id) throws CustomException;
    Page<TopSellingProductResponse> getTopSellingProducts(Integer limit, Pageable pageable) throws CustomException;
    Page<Order> getUserOrdersWithPaginationAndSearch(Pageable pageable, String search);
    List<Order>findAll();
    Double totalRevenue();
}
