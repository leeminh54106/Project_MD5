package sq.project.md5.perfumer.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.constants.RoleName;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.FormLogin;
import sq.project.md5.perfumer.model.dto.req.FormRegister;
import sq.project.md5.perfumer.model.dto.resp.JwtResponse;
import sq.project.md5.perfumer.model.entity.Roles;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.repository.IRoleRepository;
import sq.project.md5.perfumer.repository.IUserRepository;
import sq.project.md5.perfumer.repository.IWishListRepository;
import sq.project.md5.perfumer.security.jwt.JwtProvider;
import sq.project.md5.perfumer.security.principle.MyUserDetailCustom;
import sq.project.md5.perfumer.service.IAuthService;


import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IWishListRepository wishListRepository;

    @Override
    public boolean register(FormRegister formRegister) throws CustomException {

        if (userRepository.existsByUsername(formRegister.getUsername())){
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }

//        if(userRepository.existsByPhone(formRegister.getPhone())) {
//            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
//        }

        if (userRepository.existsByEmail(formRegister.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        Users user = Users.builder()
                .email(formRegister.getEmail())
//                .fullName(formRegister.getFullName())
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
//                .phone(formRegister.getPhone())
                .status(true)
                .createdAt(new Date())
                .updatedAt(new Date())
                .isDeleted(false)
                .build();
        if (formRegister.getRoles()!=null && !formRegister.getRoles().isEmpty()){
            Set<Roles> roles = new HashSet<>();
            formRegister.getRoles().forEach(
                    role->{
                        switch (role){
                            case "ADMIN":
                                roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN).orElseThrow(() -> new NoSuchElementException("Vai trò không tìm thấy")));
                            case "MANAGER":
                                roles.add(roleRepository.findByRoleName(RoleName.ROLE_MANAGER).orElseThrow(() -> new NoSuchElementException("Vai trò không tìm thấy")));
                            case "USER":
                                roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("Vai trò không tìm thấy")));
                            default:
                                roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("Vai trò không tìm thấy")));
                        }
                    }
            );
            user.setRoles(roles);
        }else {
            // mac dinh la user
            Set<Roles> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("Vai trò không tìm thấy")));
            user.setRoles(roles);
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public JwtResponse login(FormLogin formLogin) throws CustomException {
        //Xác thực username và password
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        } catch (AuthenticationException ex) {
            throw new CustomException("Tên đăng nhập hoặc mật khẩu không chính xác",HttpStatus.BAD_REQUEST);
            //409: Lỗi không hợp lệ
        }
        MyUserDetailCustom userDetailCustom = (MyUserDetailCustom) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetailCustom);
        return JwtResponse.builder()
                .id(userDetailCustom.getId())
                .accessToken(accessToken)
                .username(userDetailCustom.getUsername())
                .fullName(userDetailCustom.getFullName())
                .email(userDetailCustom.getEmail())
                .address(userDetailCustom.getAddress())
                .avatar(userDetailCustom.getAvatar())
                .createdAt(userDetailCustom.getCreatedAt())
                .updatedAt(userDetailCustom.getUpdatedAt())
                .isDeleted(userDetailCustom.getIsDeleted())
                .phone(userDetailCustom.getPhone())
                .favourite(wishListRepository.findAllByUserId(userDetailCustom.getId()).stream().map(item -> item.getProduct().getId()).toList())
                .status(userDetailCustom.getStatus())
                .roles(userDetailCustom.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }
}
