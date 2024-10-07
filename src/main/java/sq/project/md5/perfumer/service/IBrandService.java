package sq.project.md5.perfumer.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.BrandRequest;
import sq.project.md5.perfumer.model.entity.Brand;

import java.util.List;

public interface IBrandService {
    List<Brand> getAllBrands();
    Brand getBrandById(Long id);
    Brand addBrand(BrandRequest brandRequest) throws CustomException;
    Brand updateBrand(BrandRequest brandRequest, Long id) throws CustomException;
    void deleteBrand(Long id) throws CustomException;
    Page<Brand> getBrandWithPaginationAndSorting(Pageable pageable,String search);
}
