package cs.ubb.hrelperbe.DTOs;

import cs.ubb.hrelperbe.Constants.UserType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegistrationData {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String type;
}
