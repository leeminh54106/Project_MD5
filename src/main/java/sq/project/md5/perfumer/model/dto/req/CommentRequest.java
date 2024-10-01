package sq.project.md5.perfumer.model.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    private String content;

    private Long productDetailId;

    private Boolean status;
}