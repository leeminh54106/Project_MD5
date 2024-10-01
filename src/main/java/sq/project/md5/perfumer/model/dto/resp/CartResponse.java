package sq.project.md5.perfumer.model.dto.resp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CartResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Double unitPrice;
    private Integer orderQuantity;
}
