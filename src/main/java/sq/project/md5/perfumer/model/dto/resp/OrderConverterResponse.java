package sq.project.md5.perfumer.model.dto.resp;



import sq.project.md5.perfumer.model.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverterResponse {
    public static OrderResponse changeOrderResponse(Order order) {
        List<OrderDetailResponse> products = order.getOrderDetails().stream()
                .map(orderDetails -> OrderDetailResponse.builder()
                        .productDetailId(orderDetails.getProductDetail().getId())
                        .name(orderDetails.getProductDetail().getProduct().getProductName())
                        .quantity(orderDetails.getOrderQuantity())
                        .unitPrice(orderDetails.getUnitPrice())
                        .build()).collect(Collectors.toList());
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUsers().getId())
                .serialNumber(order.getSerialNumber())
                .username(order.getUsers().getFullName())
                .note(order.getNote())
                .receiveAddress(order.getReceiveAddress())
                .receivePhone(order.getReceivePhone())
                .receiveName(order.getReceiveName())
                .totalPrice(order.getTotalPrice())
                .orderDetail(products)
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .receivedAt(order.getReceivedAt())
                .build();
    }
}
