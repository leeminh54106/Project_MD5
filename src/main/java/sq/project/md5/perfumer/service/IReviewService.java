package sq.project.md5.perfumer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.BrandRequest;
import sq.project.md5.perfumer.model.dto.req.ReviewRequest;
import sq.project.md5.perfumer.model.entity.Brand;
import sq.project.md5.perfumer.model.entity.Review;

public interface IReviewService {
    Review getReviewById(Long id);
    Review addReview(ReviewRequest reviewRequest) throws CustomException;
    Review updateReview(ReviewRequest reviewRequest, Long id) throws CustomException;
    void deleteReview(Long id) throws CustomException;
    Page<Review> getReviewWithPaginationAndSorting(Pageable pageable, String search);
}
