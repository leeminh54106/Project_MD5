package sq.project.md5.perfumer.model.dto.req;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private Long userId;

    private Double totalPrice;

    private String receiveName;

    private String receiveAddress;

    private String receivePhone;

    private String note;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date receivedAt;

    private Long couponId;

    private Long reviewId;
}