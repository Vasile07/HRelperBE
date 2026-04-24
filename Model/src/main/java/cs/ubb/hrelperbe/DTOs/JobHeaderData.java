package cs.ubb.hrelperbe.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobHeaderData {
    private Integer idJob;
    private String role;
    private String department;
}
