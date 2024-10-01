package sq.project.md5.perfumer.model.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerRequest {

    private String bannerName;

    private String urlImage;
}
