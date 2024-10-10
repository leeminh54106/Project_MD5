package sq.project.md5.perfumer.model.dto.req;

import lombok.*;
import sq.project.md5.perfumer.model.entity.Product;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class WishListRequest {
    private Long id;
    private Long userId;
    private Long productId;
}
