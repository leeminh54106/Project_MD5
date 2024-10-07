package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.constants.OrderStatus;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.ReviewRequest;
import sq.project.md5.perfumer.model.entity.Order;
import sq.project.md5.perfumer.model.entity.Review;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.repository.IOrderRepository;
import sq.project.md5.perfumer.repository.IReviewRepository;
import sq.project.md5.perfumer.service.IOrderService;
import sq.project.md5.perfumer.service.IReviewService;
import sq.project.md5.perfumer.service.IUserService;

import java.util.Date;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {
    private final IReviewRepository reviewRepository;
    private final IOrderRepository orderRepository;
    private final IUserService userService;
    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại review với id: " + id));
    }

    @Override
    public Review addReview(ReviewRequest reviewRequest) throws CustomException {
        if(reviewRequest.getRate() < 1 || reviewRequest.getRate() > 5) {
            throw new NoSuchElementException("Giá trị đánh giá phải nằm trong khoảng từ 1 đến 5 ");
        }
        Users user = userService.getCurrentLoggedInUser();
       Order order = orderRepository.findByIdAndUsers(reviewRequest.getOrderId(),user).orElseThrow(() -> new NoSuchElementException("Không tồn tại order cần đánh giá. "  ));
        if(order.getReview() != null){
            throw new NoSuchElementException("Bạn đã đánh giá sản phẩm rồi!");
        }
       if(user != null && order.getStatus().equals(OrderStatus.SUCCESS)){
           Review review = Review.builder()
                   .content(reviewRequest.getContent())
                   .rate(reviewRequest.getRate())
                   .created(new Date())
                   .build();
           review = reviewRepository.save(review);
           order.setReview(review);
           orderRepository.save(order);
           return review ;
       }
       throw new NoSuchElementException("Yêu cầu nhập đúng thông tin.");
    }

    @Override
    public Review updateReview(ReviewRequest reviewRequest, Long id) throws CustomException {
        return null;
    }

    @Override
    public void deleteReview(Long id) throws CustomException {
        reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại review: " + id));
        reviewRepository.deleteById(id);
    }

    @Override
    public Page<Review> getReviewWithPaginationAndSorting(Pageable pageable, String search) {
        return null;
    }
}
