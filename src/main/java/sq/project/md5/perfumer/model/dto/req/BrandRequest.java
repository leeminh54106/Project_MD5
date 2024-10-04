package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandRequest {

    @NotBlank(message = "Tên thương hiệu không được để trống")
    private String brandName;


    private String description;

//    private Long productId;

    private Boolean status = true;
}
