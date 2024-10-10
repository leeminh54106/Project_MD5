package sq.project.md5.perfumer.model.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductResponse {
    private Long id;

    private String sku;

    private String productName;

    private String description;

    private String guarantee;

    private String instruct;

    private String image;

    List<ProductDetailResponse> productDetailResponses;
}
