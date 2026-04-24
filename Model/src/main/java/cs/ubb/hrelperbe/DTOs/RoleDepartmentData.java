package cs.ubb.hrelperbe.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDepartmentData {
    private Integer roleId;
    private String roleName;
    private Integer departmentId;
    private String departmentName;
}