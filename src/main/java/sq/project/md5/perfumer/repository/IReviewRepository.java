package sq.project.md5.perfumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sq.project.md5.perfumer.model.entity.Review;

public interface IReviewRepository extends JpaRepository<Review, Long> {

}
