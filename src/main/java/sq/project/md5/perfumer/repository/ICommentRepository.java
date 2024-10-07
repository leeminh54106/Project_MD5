package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.project.md5.perfumer.model.entity.Comment;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByUserUsernameContainsIgnoreCase(String username, Pageable pageable);
}
