package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.model.entity.Product;


import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<Product> findProductsByCategory_Id(Long id);
    Page<Product> findProductByProductNameContainsIgnoreCase(String productName, Pageable pageable);
    //@Query("select p from Product p where p.productName like concat('%',:productName,'%')")
    Page<Product> findAllByProductNameContains(String productName, Pageable pageable);
    boolean existsByProductName(String productName);
    List<Product> findTop5ByOrderByIdDesc();
    @Query("select p from Product p where p.productName like %:keyword% or p.description like %:keyword%")
    List<Product> findByProductNameOrDescriptionContaining(@Param("keyword") String search);

    Page<Product> findProductByStatusTrue(Pageable pageable);

    boolean existsByCategory_Id(Long id);

    boolean existsByProductNameAndCategory_Id(String productName, Long categoryId);

//    @Query("select p from Product p order by p.createdAt asc")
//    List<Product> getLatestProducts(Pageable pageable);
    List<Product> findTop5ByOrderByCreatedAtAsc();

    List<Product> findAll(Sort sort);
}
