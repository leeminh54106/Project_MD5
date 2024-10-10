package sq.project.md5.perfumer.service;



import sq.project.md5.perfumer.model.dto.req.AddToCartRequest;
import sq.project.md5.perfumer.model.dto.resp.CartResponse;
import sq.project.md5.perfumer.model.entity.ShoppingCart;

import java.util.List;

public interface ICartService {
    ShoppingCart addToCart(AddToCartRequest addToCart);
    List<ShoppingCart> getCart() ;
    void removeProductToCart(Long id);
    void removeAllProductToCart();
    ShoppingCart updateCartQuantity(Long id, Integer quantity);

}
