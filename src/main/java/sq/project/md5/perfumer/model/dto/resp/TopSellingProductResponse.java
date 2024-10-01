package sq.project.md5.perfumer.model.dto.resp;


import lombok.*;
import sq.project.md5.perfumer.model.entity.Product;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TopSellingProductResponse {
    private Product product;
    private Long purchaseCount;
}
