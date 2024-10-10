package sq.project.md5.perfumer.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.model.dto.resp.TopSellingProductResponse;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.service.*;
import sq.project.md5.perfumer.service.impl.BannerServiceImpl;
import sq.project.md5.perfumer.service.impl.ProductDetailServiceImpl;


import java.util.List;

@RestController
@RequestMapping("/api.example.com/v1")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    private final ICategoryService categoryService;

    private final IProductService productService;

    private final IOrderService orderService;

    private final IWishListService wishListService;

    private final BannerServiceImpl bannerService;
    private final ProductDetailServiceImpl productDetailService;

    @GetMapping("/listBanner")
    public ResponseEntity<DataResponse> getAllBanners( ) {
        return new ResponseEntity<>(new DataResponse(bannerService.getAllBanners(), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/listCategories")
    public ResponseEntity<DataResponse> getAllCategoriesUser() {
        return new ResponseEntity<>(new DataResponse(categoryService.findAllNoPagination(),HttpStatus.OK),HttpStatus.OK);
    }

    //Danh sách danh mục được bán
    @GetMapping("/category/categorySale")
    public ResponseEntity<DataResponse> getProductByCategoryForSale(@PageableDefault(page = 0,size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(new DataResponse(categoryService.listCategoriesForSale(pageable), HttpStatus.OK),HttpStatus.OK);
    }

    //Danh sách sản phẩm theo danh mục
    @GetMapping("/category/{id}")
    public ResponseEntity<DataResponse> getProductByCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(productService.findProductByCategoryId(id), HttpStatus.OK),HttpStatus.OK);
    }


    //Chi tiết thông tin sản phẩm theo id
    @GetMapping("/product/{id}")
    public ResponseEntity<DataResponse> getProductById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(productService.getProductById(id), HttpStatus.OK), HttpStatus.OK);
    }


    @GetMapping("/listProductDetail/{id}")
    public ResponseEntity<DataResponse> getAllProductDetail(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(productService.  getProductDetailByProductId(id),HttpStatus.OK),HttpStatus.OK);
    }
    
    //Danh sách sản phẩm mới nhất
    @GetMapping("/product/newProduct")
    public ResponseEntity<DataResponse> getNewProduct() {
        return new ResponseEntity<>(new DataResponse(productService.getLatestProduct(), HttpStatus.OK), HttpStatus.OK);
    }

    // Tìm kiếm sản phẩm theo tên hoặc mô tả
    @GetMapping("/product/search")
    public ResponseEntity<DataResponse> searchProduct(@RequestParam(defaultValue = "" )String search) {
        return new ResponseEntity<>(new DataResponse(productService.findProductByProductNameOrDescription(search), HttpStatus.OK),HttpStatus.OK);
    }

    //Danh sách sản phẩm mới
    @GetMapping("/product/top")
    public ResponseEntity<DataResponse> getTopProduct() {
        return new ResponseEntity<>(new DataResponse(productService.getProuductTop5(), HttpStatus.OK), HttpStatus.OK);
    }


    //Danh sách sản phẩm được bán
    @GetMapping("/product/productSale")
    public ResponseEntity<DataResponse> getProductForSale(@PageableDefault(page = 0,size = 2, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(new DataResponse(productService.listProductsForSale(pageable), HttpStatus.OK),HttpStatus.OK);
    }

    //Danh sách sản phẩm bán chạy
    @GetMapping("/top-selling-products")
    public ResponseEntity<DataResponse> getTopSellingProducts(
            @RequestParam(defaultValue = "5") Integer limit,
            @PageableDefault(page = 0, size = 5, sort = "purchaseCount", direction = Sort.Direction.DESC) Pageable pageable) throws CustomException {
        Page<TopSellingProductResponse> topSellingProducts = orderService.getTopSellingProducts(limit, pageable);
        return new ResponseEntity<>(new DataResponse(topSellingProducts,HttpStatus.OK), HttpStatus.OK);
    }

    //Danh sách sản phảm nổi bật
    @GetMapping("/top-outstanding-products")
    public ResponseEntity<DataResponse> getTopWishlistProducts(@RequestParam(defaultValue = "5") Integer limit) throws CustomException {
        List<Product> topWishlistProducts = wishListService.getTopWishlistProducts(limit);
        return new ResponseEntity<>(new DataResponse(topWishlistProducts, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/productDetails/{id}")
    public ResponseEntity<DataResponse> getProductDetailById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(productDetailService.getProductDetailById(id), HttpStatus.OK), HttpStatus.OK);
    }

    //    response product
    @GetMapping("/productByProductResponse/{id}")
    public ResponseEntity<DataResponse> getProductByProductResponse(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(productService.getProductResponseByProductId(id), HttpStatus.OK), HttpStatus.OK);
    }
}
