package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.project.md5.perfumer.model.entity.Banner;
import sq.project.md5.perfumer.model.entity.Brand;

public interface IBrandRepository extends JpaRepository<Brand, Long> {
    Page<Brand> findAllByBrandNameContains(String brandName, Pageable pageable);
    boolean existsByBrandName(String brandName);
    boolean existsBrandById(Long id);
}
