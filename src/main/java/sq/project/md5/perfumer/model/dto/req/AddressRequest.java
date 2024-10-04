package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class AddressRequest {
    @NotBlank(message = "Địa chỉ không được để trống")
    private String fullAddress;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(min = 10, max = 15, message = "Số điện thoại phải từ 10 đến 15 ký tự")
    private String phone;
    @NotBlank(message = "Tên người nhận không được để trống")
    private String receiveName;

    private Boolean isDefault = true;
}
