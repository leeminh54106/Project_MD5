package sq.project.md5.perfumer.model.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequest {

    private String code;

    private String percent;

    private Double stock;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date stardate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date enddate;
}