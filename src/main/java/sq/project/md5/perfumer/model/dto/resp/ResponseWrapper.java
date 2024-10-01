package sq.project.md5.perfumer.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sq.project.md5.perfumer.constants.EHttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseWrapper<T> {
    EHttpStatus eHttpStatus;
    int statusCode;
    T data;
}
