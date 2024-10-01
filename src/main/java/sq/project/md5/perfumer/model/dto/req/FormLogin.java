package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class FormLogin {
    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}
