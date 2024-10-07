package sq.project.md5.perfumer.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.WishListRequest;
import sq.project.md5.perfumer.model.dto.resp.WishListResponse;
import sq.project.md5.perfumer.model.entity.Product;

import java.util.List;

public interface IWishListService {
    WishListResponse addWishList(WishListRequest wishListRequest) throws CustomException;
    Page<WishListResponse> getAllWishList(Pageable pageable) throws CustomException;
    void deleteWishList(Long id) throws CustomException;
    List<Product> getTopWishlistProducts(Integer limit) throws CustomException;

}
