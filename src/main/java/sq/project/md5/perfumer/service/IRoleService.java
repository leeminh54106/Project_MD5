package sq.project.md5.perfumer.service;



import sq.project.md5.perfumer.constants.RoleName;
import sq.project.md5.perfumer.model.entity.Roles;

import java.util.List;

public interface IRoleService {
    List<Roles> getAllRoles();
    Roles findByRoleName(RoleName roleName);
}
