package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sq.project.md5.perfumer.model.entity.Image;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.model.entity.ProductDetail;
import sq.project.md5.perfumer.model.entity.WishList;

import java.util.List;
import java.util.Optional;

public interface IProductDetailRepository extends JpaRepository<ProductDetail,Long> {

    List<ProductDetail> findProductDetailByProductId(Long id);
    Page<ProductDetail> findProductDetailByProductIdContainsIgnoreCase(Long productId, Pageable pageable);

    Page<ProductDetail> findAllByProductIdContains(Long productId, Pageable pageable);

    @Query("select p from Product p where p.productName like %:keyword% or p.description like %:keyword%")
    List<Product> findByProductNameOrDescriptionContaining(@Param("keyword") String search);

    List<ProductDetail> findByProduct(Product product);
    boolean existsByProductIdAndVolume(Long id, Long volume);

    ProductDetail findByProductIdAndVolume(Long productId, Long volume);

//    boolean existsByProductIdAnd(String productName, Long categoryId);

//    find all pagination
    Page<ProductDetail> findAllByProductId (Long id,Pageable pageable);

}
