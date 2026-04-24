package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.BaseModels.Role;

import java.util.List;

public interface RoleRepositoryInterface {
    public Role getRoleById(Integer roleId);

    public List<Role> getAllRoles();
}
