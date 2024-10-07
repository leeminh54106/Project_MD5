package sq.project.md5.perfumer.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.ReviewRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.service.impl.ReviewServiceImpl;

@RestController
@RequestMapping("/api.example.com/v1/user/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    @PostMapping
    public ResponseEntity<DataResponse> WriteReview(@Valid @RequestBody ReviewRequest reviewRequest) throws CustomException {
       reviewService.addReview(reviewRequest);
        return new ResponseEntity<>(new DataResponse("Bạn đã đánh giá thành công", HttpStatus.OK), HttpStatus.OK);
    }
}
