package cs.ubb.hrelperbe.BaseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    private Integer jobId;
    private Role role;
    private String description;
    private List<Technology> technologies;
}
