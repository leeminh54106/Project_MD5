package sq.project.md5.perfumer.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.model.dto.req.CommentRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.service.impl.CommentServiceImpl;

@RestController
@RequestMapping("/api.example.com/v1/user/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;
    @PostMapping
    public ResponseEntity<DataResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) {
        commentService.addComment(commentRequest);
        return new ResponseEntity<>(new DataResponse("Bạn đã bình luận thành công!", HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteCommentUser(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new DataResponse("Bạn đã xóa bình luận thành công!", HttpStatus.OK), HttpStatus.OK);
    }
}
