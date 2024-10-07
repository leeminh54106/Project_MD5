package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddToCartRequest {
    private Long productDetailId;
    private Integer quantity;

}
