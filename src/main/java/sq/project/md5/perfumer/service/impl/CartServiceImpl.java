package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.advice.SuccessException;
import sq.project.md5.perfumer.model.dto.req.AddToCartRequest;
import sq.project.md5.perfumer.model.dto.resp.CartResponse;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.model.entity.ProductDetail;
import sq.project.md5.perfumer.model.entity.ShoppingCart;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.repository.ICartRepository;
import sq.project.md5.perfumer.repository.IProductDetailRepository;
import sq.project.md5.perfumer.repository.IProductRepository;
import sq.project.md5.perfumer.service.ICartService;
import sq.project.md5.perfumer.service.IUserService;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final ICartRepository cartRepository;

    private final IUserService userService;
    private final IProductDetailRepository productDetailRepository;
    private final IProductRepository productRepository;


    @Override
    public ShoppingCart addToCart(AddToCartRequest addToCart) {

        if (addToCart.getProductDetailId() == null) {
            throw new IllegalArgumentException("Mã sản phẩm không được phép rỗng");
        }
        if (addToCart.getQuantity() == null || addToCart.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng không để trống và số lượng phải lớn hơn 0");
        }

        Users user = userService.getCurrentLoggedInUser();
        ProductDetail productDetail = productDetailRepository.findById(addToCart.getProductDetailId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm này"));

        if(!productDetail.getStatus()) {
            throw new IllegalArgumentException("Sản phẩm này không được bán hoặc đã hết hàng vui lòng chọn sản phẩm khác");
        }

        if (addToCart.getQuantity() > productDetail.getStockQuantity()) {
            throw new IllegalArgumentException("Số lượng sản phẩm không đủ trong kho");
        }

        ShoppingCart shoppingCart = cartRepository.findByUsersAndProductDetail(user, productDetail)
                .orElse(ShoppingCart.builder()
                        .users(user)
                        .productDetail(productDetail)
                        .orderQuantity(0)
                        .build());
        shoppingCart.setOrderQuantity(shoppingCart.getOrderQuantity() + addToCart.getQuantity());
        if (shoppingCart.getOrderQuantity() > productDetail.getStockQuantity()) {
            throw new NoSuchElementException("Số lượng sản phẩm trong giỏ hàng vượt quá số lượng có sẵn trong kho");
        }
        return cartRepository.save(shoppingCart);
    }

    @Override
    public List<CartResponse> getCart() {
        Users user = userService.getCurrentLoggedInUser();
        List<ShoppingCart> shoppingCarts = cartRepository.findAllByUsers(user);
        if (shoppingCarts.isEmpty()) {
            throw new SuccessException("Giỏ hàng trống");
        }
        return shoppingCarts.stream().map(cart -> CartResponse.builder()
                        .id(cart.getId())
                        .productId(cart.getProductDetail().getId())
                        .productName(cart.getProductDetail().getProduct().getProductName())
                        .unitPrice(cart.getProductDetail().getUnitPrice())
                        .orderQuantity(cart.getOrderQuantity())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public void removeProductToCart(Long id) {
        Users user = userService.getCurrentLoggedInUser();
        ShoppingCart shoppingCart = cartRepository.findByIdAndUsers(id, user)
                .orElseThrow(() -> new NoSuchElementException("Không có sản phẩm trong giỏ hàng"));
        if (!shoppingCart.getUsers().equals(user)) {
            throw new IllegalArgumentException("Không tìm thấy người dùng này.");
        }
        cartRepository.delete(shoppingCart);
    }


    @Override
    public void removeAllProductToCart() {
        Users user = userService.getCurrentLoggedInUser();
        List<ShoppingCart> shoppingCarts = cartRepository.findAllByUsers(user);
        cartRepository.deleteAll(shoppingCarts);
    }

    @Override
    public ShoppingCart updateCartQuantity(Long id, Integer quantity) {
        Users user = userService.getCurrentLoggedInUser();
        ShoppingCart shoppingCart = cartRepository.findByIdAndUsers(id, user)
                .orElseThrow(() -> new NoSuchElementException("Không có sản phẩm trong giỏ hàng"));

        ProductDetail productDetail = productDetailRepository.findById(shoppingCart.getProductDetail().getId())
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại sản phẩm này"));

        if(quantity > productDetail.getStockQuantity()){
            throw  new NoSuchElementException("Số lượng yêu cầu vượt quá số lượng sản phẩm");
        }

        shoppingCart.setOrderQuantity(quantity);
        return cartRepository.save(shoppingCart);
    }
}
