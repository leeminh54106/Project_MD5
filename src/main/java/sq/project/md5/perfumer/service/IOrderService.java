package sq.project.md5.perfumer.service;



import sq.project.md5.perfumer.constants.OrderStatus;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.OrderRequest;
import sq.project.md5.perfumer.model.dto.resp.OrderResponse;
import sq.project.md5.perfumer.model.dto.resp.TopSellingProductResponse;
import sq.project.md5.perfumer.model.entity.Order;

import java.util.List;

public interface IOrderService {
    Order orderNow(OrderRequest orderRequest);
    List<Order> getAllOrders();
    List<OrderResponse> getOrderResponsesByStatus(OrderStatus status) throws CustomException;
    OrderResponse getOrderById(Long id);
    boolean updateOrderStatus(Long id, OrderStatus status) throws CustomException;
    List<Order> getAllUserOrders();
    Order getOrderBySerialNumber(String serialNumber);
    List<Order> getOrdersByStatus(OrderStatus orderStatus);
    boolean cancelOrder(Long id) throws CustomException;
    List<TopSellingProductResponse> getTopSellingProducts(Integer limit) throws CustomException;
}
