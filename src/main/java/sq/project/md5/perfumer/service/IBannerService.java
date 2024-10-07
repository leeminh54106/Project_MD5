package sq.project.md5.perfumer.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.BannerRequest;
import sq.project.md5.perfumer.model.entity.Banner;

import java.util.List;

public interface IBannerService {
    List<Banner> getAllBanners();
    Banner getBannerById(Long id);
    Banner addBanner(BannerRequest bannerRequest) throws CustomException;
    Banner updateBanner(BannerRequest bannerRequest, Long id) throws CustomException;
    void deleteBanner(Long id) throws CustomException;
    Page<Banner> getBannerWithPaginationAndSorting(Pageable pageable, String search);

}
