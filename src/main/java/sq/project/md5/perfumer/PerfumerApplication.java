package sq.project.md5.perfumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import sq.project.md5.perfumer.constants.RoleName;
import sq.project.md5.perfumer.model.entity.Roles;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.repository.IUserRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@SpringBootApplication
public class PerfumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerfumerApplication.class, args);
	}
//    @Bean
//    public CommandLineRunner runner(PasswordEncoder passwordEncoder, IUserRepository userRepository){
//        return args -> {
//            Roles admin = new Roles(null, RoleName.ROLE_ADMIN);
//            Roles user = new Roles(null, RoleName.ROLE_USER);
//            Roles manager = new Roles(null, RoleName.ROLE_MANAGER);
//            Set<Roles> set = new HashSet<>();
//            set.add(admin);
//            set.add(user);
//            set.add(manager);
//           Users roleAdmin = new Users(null,"admin123","admin123@gmail.com","admin",passwordEncoder.encode("admin123"), null, "0353090212", null,new Date(), new Date(), true, false, set);
//           userRepository.save(roleAdmin);
//        };
//    }
}
