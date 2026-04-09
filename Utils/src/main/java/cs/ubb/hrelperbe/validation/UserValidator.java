package cs.ubb.hrelperbe.validation;

import cs.ubb.hrelperbe.BaseModels.User;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {
    public void validate(User user){
        String errors = "";

        if (user.getName().isEmpty())
            errors += "Invalid name!\n";

        if (user.getSurname().isEmpty())
            errors += "Invalid surname!\n";

        if (!Pattern.matches("^[a-zA-Z0-9@!#$%^&*?<>]{8,}$", user.getPassword())) {
            errors += "The password must be at least 8 characters long!\n";
        }
        if (!Pattern.matches(".*[a-z].*", user.getPassword())) {
            errors += "The password must contain at least one lowercase letter!\n";
        }
        if (!Pattern.matches(".*[A-Z].*", user.getPassword())) {
            errors += "The password must contain at least one uppercase letter!\n";
        }
        if (!Pattern.matches(".*[0-9].*", user.getPassword())) {
            errors += "The password must contain at least one digit!\n";
        }
        if (!Pattern.matches(".*[@!#$%^&*?<>].*", user.getPassword())) {
            errors += "The password must contain at least one special character (@, !, #, $, %, ^, &, *, ?, <, >)!\n";
        }


        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", user.getEmail()))
            errors += "Invalid email!\n";

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }

    public void passwordValidator(String password) {
        String errors = "";

        if (!Pattern.matches("^[a-zA-Z0-9@!#$%^&*?<>]{8,}$", password)) {
            errors += "The password must be at least 8 characters long!\n";
        }
        if (!Pattern.matches(".*[a-z].*", password)) {
            errors += "The password must contain at least one lowercase letter!\n";
        }
        if (!Pattern.matches(".*[A-Z].*", password)) {
            errors += "The password must contain at least one uppercase letter!\n";
        }
        if (!Pattern.matches(".*[0-9].*", password)) {
            errors += "The password must contain at least one digit!\n";
        }
        if (!Pattern.matches(".*[@!#$%^&*?<>].*", password)) {
            errors += "The password must contain at least one special character (@, !, #, $, %, ^, &, *, ?, <, >)!\n";
        }

        if (!errors.isEmpty())
            throw new RuntimeException(errors);
    }
}
