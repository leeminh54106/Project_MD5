package sq.project.md5.perfumer.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataResponse {
    private Object content;
    private HttpStatus status;
}