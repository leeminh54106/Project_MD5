package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.model.entity.OrderDetails;
import sq.project.md5.perfumer.model.entity.ProductDetail;

import java.util.List;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetails, Long> {
//    List<OrderDetails> findByProductDetail(ProductDetail productDetail);

    @Query("select od.productDetail, sum(od.orderQuantity) AS purchaseCount " +
            "from OrderDetails od " +
            "group by od.productDetail " +
            "ORDER BY SUM(od.orderQuantity) DESC")
    List<Object[]> findTopSellingProducts(Pageable pageable);
}
