package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.Role;
import cs.ubb.hrelperbe.DTOs.RoleDepartmentData;
import cs.ubb.hrelperbe.Interfaces.RoleRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.RoleServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImplementation implements RoleServiceInterface {
    private final RoleRepositoryInterface roleRepository;

    public RoleServiceImplementation(RoleRepositoryInterface roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDepartmentData> getAllRoles() {
        return roleRepository.getAllRoles()
                .stream()
                .map(this::toRoleDepartmentData)
                .toList();
    }

    private RoleDepartmentData toRoleDepartmentData(Role role) {
        return new RoleDepartmentData(
                role.getRoleId(),
                role.getName(),
                role.getDepartment().getDepartmentId(),
                role.getDepartment().getName()
        );
    }
}