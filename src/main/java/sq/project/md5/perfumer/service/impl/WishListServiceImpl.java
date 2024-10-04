package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.project.md5.perfumer.advice.SuccessException;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.WishListRequest;
import sq.project.md5.perfumer.model.dto.resp.WishListResponse;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.model.entity.WishList;
import sq.project.md5.perfumer.repository.IProductRepository;
import sq.project.md5.perfumer.repository.IWishListRepository;
import sq.project.md5.perfumer.service.IUserService;
import sq.project.md5.perfumer.service.IWishListService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements IWishListService {

    private final IUserService userService;

    private final IProductRepository productRepository;

    private final IWishListRepository wishListRepository;

    @Override
    public WishListResponse addWishList(WishListRequest wishListRequest) throws CustomException {
        Users user = userService.getCurrentLoggedInUser();
        Product product = productRepository.findById(wishListRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Sản phẩm này không tồn tại"));

        if (wishListRepository.existsByUserAndProduct(user, product)) {
            wishListRepository.deleteByUserIdAndProductId(user.getId(),product.getId());
//            return null;
            throw new SuccessException("Đã xóa thành công sản phẩm yêu thích");
        }

        WishList wishList = WishList.builder()
                .user(user)
                .product(product)
                .build();

        wishList = wishListRepository.save(wishList);

        // Chuyển đổi WishList thành WishListResponse
        WishListResponse response = new WishListResponse();
        response.setId(wishList.getId());
        response.setWishlistProName(product.getProductName());
        response.setProductId(product.getId());
        response.setUserId(user.getId());
        return response;
    }

    @Override
    public List<WishListResponse> getAllWishList() throws CustomException {
        Users user = userService.getCurrentLoggedInUser();
        List<WishList> wishList = wishListRepository.findAllByUser(user);
        if (wishList.isEmpty()) {
            throw new CustomException("Không có sản phẩm nào trong danh sách yêu thích", HttpStatus.BAD_REQUEST);
        }
        List<WishListResponse> responseList = wishList.stream()
                .map(wish -> { WishListResponse response = new WishListResponse();
                    response.setId(wish.getId());
                    response.setProductId(wish.getProduct().getId());
                    response.setWishlistProName(wish.getProduct().getProductName());
                    response.setUserId(user.getId());
                    return response;}).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public void deleteWishList(Long id) throws CustomException {
        Users user = userService.getCurrentLoggedInUser();
        WishList wishList = wishListRepository.findByIdAndUser(id, user).orElseThrow(()-> new CustomException("không tồn tại mã sản phẩm yêu thích này", HttpStatus.BAD_REQUEST));
        if (wishList.getUser().getId().equals(user.getId())) {
            wishListRepository.delete(wishList);
            throw new SuccessException("Đã xóa thành công sản phẩm yêu thích");
        } else {
            throw new CustomException("Không tồn tại sản phẩm yêu thích của bạn", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Product> getTopWishlistProducts(Integer limit) throws CustomException {
        Pageable pageable = PageRequest.of(0, limit);
        List<Product> topWishlistProducts = wishListRepository.findTopWishlistProducts(pageable);

        if (topWishlistProducts.isEmpty()) {
            throw new CustomException("Không có sản phẩm nổi bật nào ", HttpStatus.BAD_REQUEST);
        }

        return topWishlistProducts;
    }
}
