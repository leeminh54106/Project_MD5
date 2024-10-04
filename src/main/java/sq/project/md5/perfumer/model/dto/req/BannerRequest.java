package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerRequest {

    private String bannerName;

    @NotNull(message = "Ảnh không được để trống")
    private MultipartFile urlImage;
}
