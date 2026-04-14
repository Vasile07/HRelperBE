package cs.ubb.hrelperbe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private HttpStatus statusCode;

    public CustomException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
