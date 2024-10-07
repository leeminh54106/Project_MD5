package sq.project.md5.perfumer.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.constants.OrderStatus;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.OrderRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.model.dto.resp.OrderConverterResponse;
import sq.project.md5.perfumer.model.dto.resp.OrderResponse;
import sq.project.md5.perfumer.model.entity.Order;
import sq.project.md5.perfumer.service.IOrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api.example.com/v1/user")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/cart/checkout")
    public ResponseEntity<DataResponse> orderNow(@Valid @RequestBody OrderRequest orderRequest)  {
        orderService.orderNow(orderRequest);
        return new ResponseEntity<>(new DataResponse("Bạn đã đặt hàng thành công", HttpStatus.OK), HttpStatus.OK);
    }

    //Lấy ra danh sách lịch sử mua hàng theo trạng thái đơn hàng
    @GetMapping("/order/historyStatus/{orderStatus}")
    public ResponseEntity<DataResponse> getOrderHistoryByStatus(
            @PathVariable OrderStatus orderStatus,
            @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Order> ordersPage = orderService.getOrdersByStatus(orderStatus, pageable);
        return new ResponseEntity<>(new DataResponse(ordersPage, HttpStatus.OK), HttpStatus.OK);
    }

    //Hủy đơn hàng đang chờ xác nhận
    @PutMapping("/order/historyUser/cancel/{id}")
    public ResponseEntity<DataResponse> cancelOrder(@PathVariable Long id) throws CustomException {
        boolean result = orderService.cancelOrder(id);
        if (result) {
            return new ResponseEntity<>(new DataResponse("Đơn hàng đã được hủy thành công!", HttpStatus.OK), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new DataResponse("Không thể hủy đơn hàng. Đơn hàng không ở trạng thái chờ xác nhận.", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    //Lấy ra chi tiết đơn hàng theo số serial
    @GetMapping("/order/historySerial/{serialNumber}")
    public ResponseEntity<DataResponse> getOrderHistoryBySerialNumber(@PathVariable("serialNumber") String serialNumber) {
        Order order = orderService.getOrderBySerialNumber(serialNumber);
        OrderResponse orderResponse = OrderConverterResponse.changeOrderResponse(order);
        return new ResponseEntity<>(new DataResponse(orderResponse, HttpStatus.OK), HttpStatus.OK);
    }

    //Lấy ra danh sách lịch sử mua hàng
//    @GetMapping("/order/history")
//    public ResponseEntity<DataResponse> getAllOrderHistory() {
//        List<Order> orders = orderService.getAllUserOrders();
//        List<OrderResponse> list = orders.stream().map(OrderConverterResponse::changeOrderResponse).collect(Collectors.toList());
//        return new ResponseEntity<>(new DataResponse(list, HttpStatus.OK), HttpStatus.OK);
//    }

    //Lấy ra danh sách lịch sử mua hàng
    @GetMapping("/order/history")
    public ResponseEntity<DataResponse> getAllOrderHistory(
            @PageableDefault(page = 0, size = 3, sort = "id",  direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "search", defaultValue = "") String search) {

        Page<Order> orders = orderService.getUserOrdersWithPaginationAndSearch(pageable, search);
//        List<OrderResponse> list = orders.getContent().stream()
//                .map(OrderConverterResponse::changeOrderResponse)
//                .collect(Collectors.toList());

        return new ResponseEntity<>(new DataResponse(orders, HttpStatus.OK), HttpStatus.OK);
    }
}
