package sq.project.md5.perfumer.model.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {
    private Long orderId;
    private String content;

    private Integer rate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
}