package sq.project.md5.perfumer.model.dto.resp;

import lombok.*;
import sq.project.md5.perfumer.model.entity.Product;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class WishListResponse {
    private Long id;
    private Long userId;
    private Product product;

}
