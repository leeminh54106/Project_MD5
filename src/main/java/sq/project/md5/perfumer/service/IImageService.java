package sq.project.md5.perfumer.service;

import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.BrandRequest;
import sq.project.md5.perfumer.model.entity.Brand;
import sq.project.md5.perfumer.model.entity.Image;

import java.util.List;

public interface IImageService {
    List<Image> getImageByProductDetail(Long productDetailId);
    void deleteBrand(Long id) throws CustomException;
}
