package sq.project.md5.perfumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.model.entity.ProductDetail;
import sq.project.md5.perfumer.model.entity.ShoppingCart;
import sq.project.md5.perfumer.model.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUsers(Users users);

    Optional<ShoppingCart> findByUsersAndProductDetail(Users users, ProductDetail productDetail);

    Optional<ShoppingCart> findByIdAndUsers(Long id, Users users);

    List<ShoppingCart> findByProductDetail(ProductDetail productDetail);
}