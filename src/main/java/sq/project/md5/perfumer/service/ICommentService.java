package sq.project.md5.perfumer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.model.dto.req.CommentRequest;
import sq.project.md5.perfumer.model.entity.Category;
import sq.project.md5.perfumer.model.entity.Comment;

public interface ICommentService {
 Comment addComment(CommentRequest commentRequest);
 void appComment(Long commentId);
 void deleteComment(Long commentId);
 Page<Comment> getCommentWithPaginationAndSorting(Pageable pageable, String search);
}
