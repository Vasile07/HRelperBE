package cs.ubb.hrelperbe.BaseModels;

import cs.ubb.hrelperbe.Constants.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private UserType type;
}
