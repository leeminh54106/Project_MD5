package sq.project.md5.perfumer.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.model.dto.req.CommentRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.model.entity.Comment;
import sq.project.md5.perfumer.service.ICommentService;
import sq.project.md5.perfumer.service.impl.CommentServiceImpl;

@RestController
@RequestMapping("/api.example.com/v1/user/comments")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @PostMapping
    public ResponseEntity<DataResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) {
        commentService.addComment(commentRequest);
        return new ResponseEntity<>(new DataResponse("Bạn đã bình luận thành công!", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<DataResponse> getCommentsByProductId(
            @PageableDefault(page = 0, size = 100, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long productId, // Sửa lại để lấy productId từ đường dẫn
            @RequestParam(value = "username", defaultValue = "") String search) {
        return new ResponseEntity<>(new DataResponse(   commentService.getCommentWithPaginationAndSortingByProductId(productId, pageable, search),HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(new DataResponse(commentService.updateComment(commentId,commentRequest),HttpStatus.OK),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteCommentUser(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new DataResponse("Bạn đã xóa bình luận thành công!", HttpStatus.OK), HttpStatus.OK);
    }

}
