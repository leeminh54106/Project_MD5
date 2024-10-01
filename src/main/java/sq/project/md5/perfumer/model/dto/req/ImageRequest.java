package sq.project.md5.perfumer.model.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageRequest {

    private String image;

    private Long productDetailId;
}