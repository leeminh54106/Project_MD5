package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.BannerRequest;
import sq.project.md5.perfumer.model.entity.Banner;
import sq.project.md5.perfumer.model.entity.Category;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.model.entity.WishList;
import sq.project.md5.perfumer.repository.IBannerRepository;
import sq.project.md5.perfumer.repository.ICategoryRepository;
import sq.project.md5.perfumer.service.IBannerService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements IBannerService {
    private final IBannerRepository bannerRepository;
    private final UploadFile uploadFile;


    @Override
    public List<Banner> getAllBanners() {
        List<Banner> banners = bannerRepository.findAll();
        if (banners.isEmpty()) {
            throw new NoSuchElementException("Không có banner.");
        }
        return banners;
    }

    @Override
    public Banner getBannerById(Long id) {
        return bannerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại banner với id: " + id));
    }

    @Override
    public Banner addBanner(BannerRequest bannerRequest) throws CustomException {
        if (bannerRepository.existsByBannerName(bannerRequest.getBannerName())) {
            throw new CustomException("Tên banner đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Banner banner = Banner.builder()
                .bannerName(bannerRequest.getBannerName())
                .urlImage(uploadFile.uploadLocal(bannerRequest.getUrlImage()))
                .build();
        return bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(BannerRequest bannerRequest, Long id) throws CustomException {
        Banner existingBanner = bannerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("không tồn tại banner có mã là: " + id));

        if (!existingBanner.getBannerName().equals(bannerRequest.getBannerName())
                && bannerRepository.existsByBannerName(bannerRequest.getBannerName())) {
            throw new CustomException("Tên banner đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Banner banner = Banner.builder()
                .bannerName(bannerRequest.getBannerName())
                .urlImage(uploadFile.uploadLocal(bannerRequest.getUrlImage()))
                .build();
        return bannerRepository.save(banner);
    }

    @Override
    public void deleteBanner(Long id){
        bannerRepository.deleteById(id);
    }

    @Override
    public Page<Banner> getBannerWithPaginationAndSorting(Integer page, Integer pageSize, String sortBy, String orderBy, String searchName) {
        Pageable pageable;
        // Xác định cách sắp xếp
        if (!sortBy.isEmpty()) {
            Sort sort;
            switch (sortBy) {
                case "asc":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "desc":
                    sort = Sort.by(orderBy).descending();
                    break;
                default:
                    sort = Sort.by(orderBy).ascending();
            }
            pageable = PageRequest.of(page, pageSize, sort);
        } else {
            pageable = PageRequest.of(page, pageSize);
        }


        Page<Banner> bannersPage;
        if (searchName.isEmpty()) {
            bannersPage = bannerRepository.findAll(pageable);
        } else {
            bannersPage = bannerRepository.findAllByBannerNameContains(searchName, pageable);
        }

        if (bannersPage.isEmpty()) {
            throw new NoSuchElementException("Không tìm thấy banner");
        }

        return bannersPage;
    }

}
