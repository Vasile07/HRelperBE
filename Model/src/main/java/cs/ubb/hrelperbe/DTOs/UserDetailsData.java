package cs.ubb.hrelperbe.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsData {
    private String name;
    private String email;
    private String type;
}