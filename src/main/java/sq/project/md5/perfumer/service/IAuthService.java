package sq.project.md5.perfumer.service;


import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.FormLogin;
import sq.project.md5.perfumer.model.dto.req.FormRegister;
import sq.project.md5.perfumer.model.dto.resp.JwtResponse;

public interface IAuthService {
    boolean register(FormRegister formRegister) throws CustomException;

    JwtResponse login(FormLogin formLogin) throws CustomException;

}
