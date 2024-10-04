package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.BrandRequest;
import sq.project.md5.perfumer.model.entity.Brand;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.repository.IBrandRepository;
import sq.project.md5.perfumer.service.IBrandService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements IBrandService {
    private final IBrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        List<Brand> brand = brandRepository.findAll();
        if (brand.isEmpty()) {
            throw new NoSuchElementException("Không có thương hiệu.");
        }
        return brand;
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại thương hiệu với id: " + id));
    }

    @Override
    public Brand addBrand(BrandRequest brandRequest) throws CustomException {
        if (brandRepository.existsByBrandName(brandRequest.getBrandName())) {
            throw new CustomException("Tên thương hiệu đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Brand brand = Brand.builder()
                .brandName(brandRequest.getBrandName())
                .description(brandRequest.getDescription())

                .build();
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(BrandRequest brandRequest, Long id) throws CustomException {
        Brand existingBrand = brandRepository.findById(id).orElseThrow(() -> new NoSuchElementException("không tồn tại thương hiệu có mã là: " + id));

        if (!existingBrand.getBrandName().equals(brandRequest.getBrandName())
                && brandRepository.existsByBrandName(brandRequest.getBrandName())) {
            throw new CustomException("Tên thương hiệu đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Brand brand = Brand.builder()
                .id(existingBrand.getId())
                .brandName(brandRequest.getBrandName())
                .description(brandRequest.getDescription())
                .status(brandRequest.getStatus())
                .build();
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Long id) throws CustomException {
        brandRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại thương hiệu: " + id));
    brandRepository.deleteById(id);
    }

    @Override
    public Page<Brand> getBrandWithPaginationAndSorting(Integer page, Integer pageSize, String sortBy, String orderBy, String searchName) {
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


        Page<Brand> brandsPage;
        if (searchName.isEmpty()) {
            brandsPage = brandRepository.findAll(pageable);
        } else {
            brandsPage = brandRepository.findAllByBrandNameContains(searchName, pageable);
        }

        if (brandsPage.isEmpty()) {
            throw new NoSuchElementException("Không tìm thấy thương hiệu");
        }

        return brandsPage;
    }

}
