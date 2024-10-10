package sq.project.md5.perfumer.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardResponse {
    private Integer totalUser;
    private Integer totalOrder;
    private Integer totalProduct;
    private Double revenue;

}
