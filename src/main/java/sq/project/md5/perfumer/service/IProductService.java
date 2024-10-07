package sq.project.md5.perfumer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.ProductRequest;
import sq.project.md5.perfumer.model.entity.Product;


import java.util.List;

public interface IProductService {
    Page<Product> getAllProduct(Pageable pageable, String search);
    Product getProductById(Long id);
    Product addProduct(ProductRequest product) throws CustomException;
    Product updateProduct(ProductRequest product, Long id) throws CustomException;
    void deleteProduct(Long id) throws CustomException;
    Page<Product> getProductWithPaginationAndSorting(Pageable pageable,String search);
    List<Product> findProductByCategoryId(Long id);
    List<Product> getLatestProduct();
    List<Product> findProductByProductNameOrDescription(String search);

    Page<Product> listProductsForSale(Pageable pageable);

    List<Product> getProductsSortedByPrice();
}
