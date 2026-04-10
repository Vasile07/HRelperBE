package cs.ubb.hrelperbe.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostData {
    private Integer roleId;
    private Integer departmentId;
    private String description;
    private List<String> skills;
    private List<Integer> technologies;
    private List<String> guides;
}
