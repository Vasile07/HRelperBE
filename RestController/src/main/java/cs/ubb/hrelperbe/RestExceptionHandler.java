package cs.ubb.hrelperbe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleEntityNotFound(CustomException customException) {
        return new ResponseEntity<>(customException.getMessage(), customException.getStatusCode());
    }
}
