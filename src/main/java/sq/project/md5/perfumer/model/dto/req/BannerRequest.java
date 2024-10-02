package sq.project.md5.perfumer.model.dto.req;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerRequest {

    private String bannerName;

    private MultipartFile urlImage;
}
