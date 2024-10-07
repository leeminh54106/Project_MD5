package sq.project.md5.perfumer.model.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    // trường hợp khi dùng địa chỉ của mình
    private Long addressId;

    // trường họp khi dùng địa chỉ khác
    private String receiveName;
    private String receiveFullAddress;
    private String receivePhone;

    private String note;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date receivedAt;

    private Long couponId;


}