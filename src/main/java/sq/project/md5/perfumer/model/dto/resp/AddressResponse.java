package sq.project.md5.perfumer.model.dto.resp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class AddressResponse {
    private Long id;
    private String fullAddress;
    private String phone;
    private String receiveName;
    private Boolean isDefault = true;
    private Long userId;
}
