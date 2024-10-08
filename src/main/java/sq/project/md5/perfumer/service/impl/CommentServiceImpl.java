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
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("bình luận không tồn tại."));
        comment.setStatus(true);
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
}
