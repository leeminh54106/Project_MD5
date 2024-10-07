package sq.project.md5.perfumer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sq.project.md5.perfumer.constants.EHttpStatus;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.FormLogin;
import sq.project.md5.perfumer.model.dto.req.FormRegister;
import sq.project.md5.perfumer.model.dto.resp.ResponseWrapper;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.service.IAuthService;
import sq.project.md5.perfumer.service.IUserService;


@RestController
@RequestMapping("/api.example.com/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    private final IUserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> doLogin(@Valid @RequestBody FormLogin formLogin) throws CustomException {
        Users users = userService.getUserByUserName(formLogin.getUsername());
        if (users.getStatus() == Boolean.FALSE) {
            return new ResponseEntity<>(
                    ResponseWrapper.builder()
                            .eHttpStatus(EHttpStatus.FORBIDDEN)
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .data("Tài khoản của bạn đã bị chặn hoặc không tồn tại! Mời bạn đăng ký tài khoản khác")
                            .build(),
                    HttpStatus.FORBIDDEN
            );
        }
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .eHttpStatus(EHttpStatus.SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .data(authService.login(formLogin))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> doRegister(@Valid @RequestBody FormRegister formRegister) throws CustomException {
        authService.register(formRegister);
        return new ResponseEntity<>( ResponseWrapper.builder()
                .eHttpStatus(EHttpStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data("Đăng ký tài khoản thành công")
                .build(),
                HttpStatus.CREATED);
    }

}
