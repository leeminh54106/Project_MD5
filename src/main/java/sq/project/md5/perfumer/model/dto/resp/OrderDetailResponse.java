package sq.project.md5.perfumer.model.dto.resp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class OrderDetailResponse {
    private Long productDetailId;
    private Integer quantity;
    private Double unitPrice;
    private String name;
}
