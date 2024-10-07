package sq.project.md5.perfumer.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sq.project.md5.perfumer.constants.OrderStatus;


import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class OrderResponse {
    private Long id;

    private String serialNumber;

    private String username;

    private Long userId;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String receiveName;
    private String receiveAddress;
    private String receivePhone;

    private String note;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date receivedAt;

    private List<OrderDetailResponse> orderDetail;
}
