package sq.project.md5.perfumer.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.ProductDetailRequest;
import sq.project.md5.perfumer.model.dto.req.ProductRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.service.impl.ProductDetailServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api.example.com/v1/admin/productDetails")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailServiceImpl productDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> getProductDetailById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(productDetailService.getProductDetailById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DataResponse> addProduct(@Valid @ModelAttribute ProductDetailRequest productDetailRequest) throws CustomException {
        return new ResponseEntity<>(new DataResponse(productDetailService.addProductDetail(productDetailRequest), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> updateProduct(@Valid @ModelAttribute ProductDetailRequest ProductDetailRequest, @PathVariable("id") Long id) throws CustomException {
        return new ResponseEntity<>(new DataResponse(productDetailService.updateProductDetail(ProductDetailRequest, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteProduct(@PathVariable("id") Long id) throws CustomException {
        productDetailService.deleteProductDetail(id);
        return new ResponseEntity<>(new DataResponse("Đã xóa thành công sản phẩm chi tiết có mã: "+id, HttpStatus.OK), HttpStatus.OK);
    }

}
