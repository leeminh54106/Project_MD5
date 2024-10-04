package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailRequest {
    @NotNull(message = "Đơn giá không được để trống")
    @Positive(message = "Đơn giá phải lớn hơn 0")
    private Double unitPrice;

//    @NotNull(message = "Ảnh không được để trống")
    private List<MultipartFile> image;

    @NotNull(message = "Số lượng không được để trống")
    @Positive(message = "Số lượng phải lớn hơn 0")
    private Integer stockQuantity;

    @NotNull(message = "Dung tích không được để trống")
    @Positive(message = "Dung tích phải lớn hơn 0")
    private Long volume;

    private Boolean status = true;

    private Long productId;


}
