package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    @NotBlank(message = "bình luận không được để trống!")
    private String content;
    @NotNull(message = "Id sản phẩm chi tiết không được để trống!")
    private Long productId;

    private Boolean status = false;
}