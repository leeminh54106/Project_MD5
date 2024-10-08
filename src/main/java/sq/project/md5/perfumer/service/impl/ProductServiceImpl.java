package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.ProductRequest;
import sq.project.md5.perfumer.model.entity.*;
import sq.project.md5.perfumer.repository.*;
import sq.project.md5.perfumer.service.IBrandService;
import sq.project.md5.perfumer.service.ICategoryService;
import sq.project.md5.perfumer.service.IProductService;


import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final IBrandService brandService;
    private final ICategoryService categoryService;

    private final UploadFile uploadFile;

    private final IWishListRepository wishListRepository;

    private final IOrderDetailRepository orderDetailRepository;

    private final ICartRepository cartRepository;

    private final IProductDetailRepository productDetailRepository;

    @Override
    public Page<Product> getAllProduct(Pageable pageable, String search) {
        Page<Product> products;
        if (search == null || search.isEmpty()) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findProductByProductNameContainsIgnoreCase(search, pageable);
        }
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại sản phẩm nào có mã: " + id));
    }

    @Override
    public Product addProduct(ProductRequest productRequest) throws CustomException {
        if (productRepository.existsByProductNameAndCategory_Id(productRequest.getProductName(), productRequest.getCategoryId())) {
            throw new CustomException("Sản phẩm đã tồn tại trong danh mục", HttpStatus.CONFLICT);
        }

        if (productRepository.existsByProductName(productRequest.getProductName())) {
            throw new CustomException("Tên sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Category category = categoryService.getCategoryById(productRequest.getCategoryId());
        if (!category.getStatus()) {
            throw new CustomException("Danh mục không hoạt động, không thể thêm sản phẩm", HttpStatus.BAD_REQUEST);
        }

        Brand brand = brandService.getBrandById(productRequest.getBrandId());
        if (!brand.getStatus()) {
            throw new CustomException("Danh mục không hoạt động, không thể thêm sản phẩm", HttpStatus.BAD_REQUEST);
        }

        Product prod = Product.builder()
                .sku(productRequest.getSku())
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .guarantee(productRequest.getGuarantee())
                .instruct(productRequest.getInstruct())
                .image(uploadFile.uploadLocal(productRequest.getImage()))
                .status(productRequest.getStatus())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        prod.setCategory(category);
        prod.setBrand(brand);
        return productRepository.save(prod);
    }

    @Override
    public Product updateProduct(ProductRequest product, Long id) throws CustomException {
        // Lấy sản phẩm hiện tại từ database
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại sản phẩm có mã: " + id));

        // Kiểm tra tên sản phẩm
        if (!existingProduct.getProductName().equals(product.getProductName())
                && productRepository.existsByProductName(product.getProductName())) {
            throw new CustomException("Tên sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST);
        }


        // Cập nhật thông tin sản phẩm
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());

        // Chỉ cập nhật ảnh nếu có ảnh mới
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            existingProduct.setImage(uploadFile.uploadLocal(product.getImage()));
        }

        existingProduct.setStatus(product.getStatus());
        existingProduct.setUpdatedAt(new Date());

        // Cập nhật danh mục
        Category category = categoryService.getCategoryById(product.getCategoryId());
        existingProduct.setCategory(category);

        // Lưu sản phẩm đã cập nhật
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) throws CustomException {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại sản phẩm: " + id));

//        List<OrderDetails> orderDetails = orderDetailRepository.findByProductDetail(product);
//        if (!orderDetails.isEmpty()) {
//            throw new CustomException("Không thể xóa sản phẩm, Sản phẩm đã tồn tại trong đơn hàng", HttpStatus.BAD_REQUEST);
//        }

//        List<WishList> wishList = wishListRepository.findByProduct(product);
//        if(!wishList.isEmpty()) {
//            throw new CustomException("Không thể xóa sản phẩm, Sản phẩm đã tồn tại trong sản phẩm yêu thích", HttpStatus.BAD_REQUEST);
//        }

        List<ProductDetail> productDetails = productDetailRepository.findByProduct(product);
        if (!productDetails.isEmpty()) {
            throw new CustomException("Không thể xóa sản phẩm, Đang tồn tại sản phẩm chi tiết!", HttpStatus.BAD_REQUEST);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getProductWithPaginationAndSorting(Pageable pageable, String search) {
        Page<Product> products;
        if (search.isEmpty()) {

            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findAllByProductNameContains(search, pageable);
        }
        if (products.isEmpty()) {
            throw new NoSuchElementException("Không tìm thấy sản phẩm có tên là:" + search);
        }
        return products;
    }

    @Override
    public List<Product> findProductByProductNameOrDescription(String search) {
        List<Product> products = productRepository.findByProductNameOrDescriptionContaining(search);

        if (products.isEmpty()) {
            throw new NoSuchElementException("Không có sản phẩm nào được tìm thấy.");
        }
        return products;
    }

    @Override
    public List<Product> findProductByCategoryId(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy danh mục với ID: " + id);
        }

        List<Product> products = productRepository.findProductsByCategory_Id(id);

        if (products.isEmpty()) {
            throw new NoSuchElementException("Danh mục với ID: " + id + " không có sản phẩm nào.");
        }
        return products;
    }

    @Override
    public List<Product> getLatestProduct() {
        return productRepository.findTop5ByOrderByCreatedAtAsc();
    }


    @Override
    public Page<Product> listProductsForSale(Pageable pageable) {
        return productRepository.findProductByStatusTrue(pageable);
    }

    @Override
    public List<Product> getProductsSortedByPrice() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "unitPrice"));
    }

//    FIND ALL PAGINATION




}
