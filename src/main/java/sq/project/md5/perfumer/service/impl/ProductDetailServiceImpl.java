package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.ProductDetailRequest;
import sq.project.md5.perfumer.model.entity.Image;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.model.entity.ProductDetail;
import sq.project.md5.perfumer.repository.IImageRepository;
import sq.project.md5.perfumer.repository.IProductDetailRepository;
import sq.project.md5.perfumer.repository.IProductRepository;
import sq.project.md5.perfumer.service.IProductDetailService;
import sq.project.md5.perfumer.service.IProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements IProductDetailService {
    private final IProductDetailRepository productDetailRepository;
    private final IProductRepository productRepository;
    private final IProductService productService;
    private final IImageRepository imageRepository;
    private final UploadFile uploadFile;

    @Override

    public ProductDetail getProductDetailById(Long id) {
        return productDetailRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại chi tiết sản phẩm nào có mã: " + id));
    }

    @Override
    public ProductDetail addProductDetail(ProductDetailRequest productDetailRequest) throws CustomException {
        if (productDetailRepository.existsByProductIdAndVolume(productDetailRequest.getProductId(), productDetailRequest.getVolume())) {

            ProductDetail existingProductDetail = productDetailRepository.findByProductIdAndVolume(productDetailRequest.getProductId(), productDetailRequest.getVolume());

            existingProductDetail.setStockQuantity(existingProductDetail.getStockQuantity() + productDetailRequest.getStockQuantity());

            return productDetailRepository.save(existingProductDetail);
        }

        Product product = productService.getProductById(productDetailRequest.getProductId());
        if (product == null) {
            throw new CustomException("Sản phẩm không tồn tại", HttpStatus.NOT_FOUND);
        }

        ProductDetail productDetail = ProductDetail.builder()
                .unitPrice(productDetailRequest.getUnitPrice())
                .stockQuantity(productDetailRequest.getStockQuantity())
                .volume(productDetailRequest.getVolume())
                .status(productDetailRequest.getStatus())
                .product(product)
                .build();
        productDetail = productDetailRepository.save(productDetail);

        List<Image> images = new ArrayList<>();

        ProductDetail finalProductDetail = productDetail;
        productDetailRequest.getImage().forEach(item -> {
            String imageUrl = uploadFile.uploadLocal(item);
            Image image = Image.builder()
                    .image(imageUrl)
                    .productDetail(finalProductDetail)
                    .build();
            images.add(image);
        });
        imageRepository.saveAll(images);

        finalProductDetail.setImage(images.get(0).getImage());
        return productDetailRepository.save(finalProductDetail);
    }

    @Override
    public ProductDetail updateProductDetail(ProductDetailRequest productDetailRequest, Long id) throws CustomException {
        ProductDetail existingProductDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new CustomException("Sản phẩm chi tiết không tồn tại", HttpStatus.NOT_FOUND));

        existingProductDetail.setUnitPrice(productDetailRequest.getUnitPrice());
        existingProductDetail.setVolume(productDetailRequest.getVolume());
        existingProductDetail.setStatus(productDetailRequest.getStatus());

        if (productDetailRequest.getStockQuantity() != null) {
            existingProductDetail.setStockQuantity(productDetailRequest.getStockQuantity());
        }
        return productDetailRepository.save(existingProductDetail);
    }

    @Override
    public void deleteProductDetail(Long id) throws CustomException {
        productDetailRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại chi tiết sản phẩm nào có mã: " + id));
       List<Image> images =  imageRepository.findByProductDetailId(id);
       imageRepository.deleteAll(images);
        productDetailRepository.deleteById(id);
    }

    @Override
    public List<ProductDetail> findProductDetailByProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy sản phẩm với ID: " + id);
        }

        List<ProductDetail> productDetails = productDetailRepository.findProductDetailByProductId(id);

        if (productDetails.isEmpty()) {
            throw new NoSuchElementException("sản phẩm với ID: " + id + " không có sản phẩm chi tiết nào.");
        }
        return productDetails;
    }
}
