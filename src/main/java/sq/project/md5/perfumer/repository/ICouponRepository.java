package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.project.md5.perfumer.model.entity.Banner;
import sq.project.md5.perfumer.model.entity.Coupon;

public interface ICouponRepository extends JpaRepository<Coupon, Long> {
    Page<Coupon> findAllByCodeContainsIgnoreCase(String code, Pageable pageable);
    boolean existsByCode(String code);
    boolean existsBannerById(Long id);
}
