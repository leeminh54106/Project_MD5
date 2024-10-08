package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.model.entity.Users;


import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    //lấy thông tin ngươ dùng qua userName
    Optional<Users> findByUsername(String username);
    Page<Users> findUsersByFullNameContainsIgnoreCase(String fullName, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByUsername(String username);
}
