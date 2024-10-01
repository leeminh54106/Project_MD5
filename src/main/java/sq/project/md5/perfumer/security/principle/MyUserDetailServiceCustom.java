package sq.project.md5.perfumer.security.principle;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.repository.IUserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailServiceCustom implements UserDetailsService {

    private final IUserRepository userRepository;

    //Tìm kiếm thông qua username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tên người dùng"));
        return MyUserDetailCustom.build(users);
    }
}

