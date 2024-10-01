package sq.project.md5.perfumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sq.project.md5.perfumer.model.entity.ProductDetail;

public interface IProductDetailRepository extends JpaRepository<ProductDetail,Long> {
}
