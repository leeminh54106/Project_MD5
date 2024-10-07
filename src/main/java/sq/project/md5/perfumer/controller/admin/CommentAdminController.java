package sq.project.md5.perfumer.controller.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.service.impl.CommentServiceImpl;

@RestController
@RequestMapping("/api.example.com/v1/admin/comments")
@RequiredArgsConstructor
public class CommentAdminController {
    private final CommentServiceImpl commentService;
    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> appComment(@PathVariable Long id) {
        commentService.appComment(id);
        return new ResponseEntity<>(new DataResponse("Bạn đã duyệt bình luận thành công!", HttpStatus.OK), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new DataResponse("Bạn đã xóa bình luận thành công!", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<DataResponse> searchCommentByUserName(@PageableDefault(page = 0, size = 3, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(value = "username", defaultValue = "") String search) {
        return new ResponseEntity<>(new DataResponse(commentService.getCommentWithPaginationAndSorting(pageable, search),HttpStatus.OK), HttpStatus.OK);
    }
}
