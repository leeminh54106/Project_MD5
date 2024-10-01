package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.constants.RoleName;
import sq.project.md5.perfumer.model.entity.Roles;
import sq.project.md5.perfumer.repository.IRoleRepository;
import sq.project.md5.perfumer.service.IRoleService;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;

    @Override
    public List<Roles> getAllRoles() {
        return roleRepository.findAll();

    }

    @Override
    public Roles findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new NoSuchElementException("Không tìm thấy vai trò"));
    }
}
