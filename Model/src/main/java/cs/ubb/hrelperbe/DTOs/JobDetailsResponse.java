package cs.ubb.hrelperbe.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDetailsResponse {
    private Integer jobId;
    private String description;
    private String role;
    private String department;
    private List<String> skills;
    private List<TechnologyResponse> technologies;
    private List<String> guides;
}
