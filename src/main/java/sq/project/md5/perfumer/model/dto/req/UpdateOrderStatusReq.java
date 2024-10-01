package sq.project.md5.perfumer.model.dto.req;

import lombok.*;
import sq.project.md5.perfumer.constants.OrderStatus;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class UpdateOrderStatusReq {
    private OrderStatus status;
}
