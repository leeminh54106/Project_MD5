package sq.project.md5.perfumer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.ProductDetailRequest;
import sq.project.md5.perfumer.model.dto.req.ProductRequest;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.model.entity.ProductDetail;

import java.util.List;

public interface IProductDetailService {

    ProductDetail getProductDetailById(Long id);
    ProductDetail addProductDetail(ProductDetailRequest productDetailRequest) throws CustomException;
    ProductDetail updateProductDetail(ProductDetailRequest productDetailRequest, Long id) throws CustomException;
    void deleteProductDetail(Long id) throws CustomException;
//    Page<ProductDetail> getProductDetailWithPaginationAndSorting(Pageable pageable,String search);
    List<ProductDetail> findProductDetailByProduct(Long id);

//  FIND ALL PAGINATION
    Page<ProductDetail> findAllPaginationByProductId(Long id,Pageable pageable);
}
