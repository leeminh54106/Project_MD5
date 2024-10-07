package sq.project.md5.perfumer.controller.admin;

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
import sq.project.md5.perfumer.model.dto.req.UpdateOrderStatusReq;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.model.dto.resp.OrderResponse;
import sq.project.md5.perfumer.model.entity.Order;
import sq.project.md5.perfumer.service.impl.OrderServiceImpl;


import java.util.List;

@RestController
@RequestMapping("/api.example.com/v1/admin/order")
@RequiredArgsConstructor
public class OrderAdminController {
    private final OrderServiceImpl orderService;

    @GetMapping
    public ResponseEntity<DataResponse> getAllOrders(@PageableDefault(page = 0,size = 3, sort = "id",
    direction = Sort.Direction.ASC) Pageable pageable,@RequestParam(value = "search",defaultValue = "")String search) {
        return new ResponseEntity<>(new DataResponse(orderService.getAllOrders(pageable,search), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/orderById/{id}")
    public ResponseEntity<DataResponse> getOrderById(@PathVariable Long id) {
        OrderResponse orderResponse = orderService.getOrderById(id);
        return new ResponseEntity<>(new DataResponse(orderResponse, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("orderStatus/{status}")
    public ResponseEntity<DataResponse> getOrderByStatus(@PathVariable OrderStatus status,
                                                         @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<OrderResponse> orderResponses = orderService.getOrderResponsesByStatus(status, pageable);
        return new ResponseEntity<>(new DataResponse(orderResponses, HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<DataResponse> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusReq status) throws CustomException {
            boolean result = orderService.updateOrderStatus(id, status.getStatus());
            if (result) {
                return new ResponseEntity<>(new DataResponse("Cập nhật trạng thái đơn hàng thành công!", HttpStatus.OK), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new DataResponse("Cập nhật trạng thái đơn hàng thất bại.", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
    }

}
