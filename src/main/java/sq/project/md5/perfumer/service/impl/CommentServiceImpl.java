package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.model.dto.req.CommentRequest;

import sq.project.md5.perfumer.model.entity.*;

import sq.project.md5.perfumer.repository.ICommentRepository;
import sq.project.md5.perfumer.repository.IProductDetailRepository;
import sq.project.md5.perfumer.repository.IProductRepository;
import sq.project.md5.perfumer.service.ICommentService;
import sq.project.md5.perfumer.service.IUserService;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final IUserService userService;
    private final IProductRepository productRepository;

    @Override
    public Comment addComment(CommentRequest commentRequest) {
        Users user = userService.getCurrentLoggedInUser();
        Product product = productRepository.findById(commentRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementException("sản phẩm không tồn tại."));
        Comment comment = Comment.builder()
                .user(user)
                .content(commentRequest.getContent())
                .product(product)
                .status(commentRequest.getStatus())
                .createdAt(new Date())
                .build();

        return commentRepository.save(comment);
    }

    @Override
    public void appComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("bình luận không tồn tại."));
        comment.setStatus(true);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Users user = userService.getCurrentLoggedInUser();
        Comment comment = commentRepository.findByIdAndUser(commentId, user)
                .orElseThrow(() -> new NoSuchElementException("bình luận không tồn tại."));
        if(!comment.getUser().equals(user)) {
            throw new IllegalArgumentException("Không tìm thấy bình luận này.");
        }
        commentRepository.delete(comment);
    }

    @Override
    public Page<Comment> getCommentWithPaginationAndSorting(Pageable pageable, String search) {
        Page<Comment> commentPage;
        if(search.isEmpty()){
            commentPage = commentRepository.findAll(pageable);
        }else {
            commentPage = commentRepository.findAllByUserUsernameContainsIgnoreCase(search,pageable);
        }
        return commentPage;
    }

    @Override
    public Page<Comment> getCommentWithPaginationAndSortingByProductId(Long productId, Pageable pageable, String search) {
        Page<Comment> commentPage;
        if (search.isEmpty()) {
            commentPage = commentRepository.findAllByProduct_Id(productId, pageable);
        } else {
            commentPage = commentRepository.findAllByProduct_Id(
                     productId, pageable);
        }
        return commentPage;
    }

    @Override
    public Comment updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new NoSuchElementException("không tồn tại user"));
        if(userService.getCurrentLoggedInUser().getId().equals(comment.getUser().getId())) {
            comment.setContent(commentRequest.getContent());
            return commentRepository.save(comment);
        }
        throw new NoSuchElementException("comment không phải của bạn");
    }

}

