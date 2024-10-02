package sq.project.md5.perfumer.model.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandRequest {

    private String brandName;

    private String description;

//    private Long productId;

    private Boolean status = true;
}
