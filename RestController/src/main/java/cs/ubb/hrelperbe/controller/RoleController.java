package cs.ubb.hrelperbe.controller;

import cs.ubb.hrelperbe.DTOs.RoleDepartmentData;
import cs.ubb.hrelperbe.Interfaces.RoleServiceInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {
    private final RoleServiceInterface roleService;

    public RoleController(RoleServiceInterface roleService) {
        this.roleService = roleService;
    }

    @GetMapping(path = "")
    public List<RoleDepartmentData> getAllRoles() {
        return roleService.getAllRoles();
    }
}