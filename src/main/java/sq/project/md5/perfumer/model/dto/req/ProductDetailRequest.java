package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotNull;

public class ProductDetailRequest {
    @NotNull(message = "Đơn giá không được để trống")
//    @Column(columnDefinition = "Decimal(10,2)")
    private Double unitPrice;

    private String image;

    @NotNull(message = "Số lượng không được để trống")
    private Integer stockQuantity;

    private Long volume;

    private Boolean status;

    private Long productId;
}
