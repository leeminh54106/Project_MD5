package sq.project.md5.perfumer.model.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class FormRegister {
    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email không đúng định dạng")
    private String email;

//    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

//    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    private Set<String> roles;
}
