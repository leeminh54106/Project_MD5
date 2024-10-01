package sq.project.md5.perfumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.constants.RoleName;
import sq.project.md5.perfumer.model.entity.Roles;


import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(RoleName roleName);
}
