package sq.project.md5.perfumer.model.dto.resp;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sq.project.md5.perfumer.model.entity.ProductDetail;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDetailResponse {
    private Long id;

    private Double unitPrice;

    private String image;

    private Integer stockQuantity;

    private Long volume;

    private Boolean status;

    private Set<String> images;

    public ProductDetailResponse(ProductDetail productDetail,Set<String> images){
        this.id = productDetail.getId();
        this.unitPrice = productDetail.getUnitPrice();
        this.image = productDetail.getImage();
        this.stockQuantity = productDetail.getStockQuantity();
        this.volume = productDetail.getVolume();
        this.status = productDetail.getStatus();
        this.images = images;
    }
}
