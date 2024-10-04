package sq.project.md5.perfumer.model.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequest {
    @NotBlank(message = "Tên phiếu giảm giá không được để trống")
    private String code;

    @NotNull(message = "Phần trăm không được để trống")
    @Positive(message = "Phần trăm phải lớn hơn 0")
    private  Double percent;

    @NotNull(message = "Phần trăm không được để trống")
    @Positive(message = "Phần trăm phải lớn hơn 0")
    private Double stock;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startdate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date enddate;
}