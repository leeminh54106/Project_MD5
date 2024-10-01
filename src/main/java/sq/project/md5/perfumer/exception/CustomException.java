package sq.project.md5.perfumer.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomException extends Exception {
    HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


}
