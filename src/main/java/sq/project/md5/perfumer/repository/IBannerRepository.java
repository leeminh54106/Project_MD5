package sq.project.md5.perfumer.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sq.project.md5.perfumer.model.entity.Banner;

public interface IBannerRepository extends JpaRepository<Banner, Long> {
    Page<Banner> findAllByBannerNameContains(String bannerName, Pageable pageable);
    boolean existsByBannerName(String bannerName);
    boolean existsBannerById(Long id);

}
