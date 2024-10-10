package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.project.md5.perfumer.model.entity.Comment;
import sq.project.md5.perfumer.model.entity.Users;

import java.util.Optional;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByUserUsernameContainsIgnoreCase(String username, Pageable pageable);
    Page<Comment> findAllByProduct_Id(Long productId, Pageable pageable);
    Optional<Comment> findByIdAndUser(Long id, Users user);
}
