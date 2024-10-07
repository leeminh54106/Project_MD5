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
import sq.project.md5.perfumer.model.dto.req.ProductRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.model.entity.Product;
import sq.project.md5.perfumer.service.impl.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api.example.com/v1/admin/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<DataResponse> getAllProducts(@PageableDefault(page = 0,size = 5, sort = "id",direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(defaultValue = "" ) String search) {
        return new ResponseEntity<>(new DataResponse(productService.getAllProduct(pageable,search),HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> getProductById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(productService.getProductById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DataResponse> addProduct(@Valid @ModelAttribute ProductRequest product) throws CustomException {
        return new ResponseEntity<>(new DataResponse(productService.addProduct(product), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> updateProduct(@Valid @ModelAttribute ProductRequest product, @PathVariable("id") Long id) throws CustomException {
        return new ResponseEntity<>(new DataResponse(productService.updateProduct(product, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteProduct(@PathVariable("id") Long id) throws CustomException {
        productService.deleteProduct(id);
        return new ResponseEntity<>(new DataResponse("Đã xóa thành công sản phẩm có mã: "+id, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/searchByProductName")
    public ResponseEntity<DataResponse> searchByCategoryName(@RequestParam(name = "searchName", defaultValue = "")String searchName,
                                                             @RequestParam(name = "page", defaultValue = "0")Integer page,
                                                             @RequestParam(name = "pageSize", defaultValue = "2")Integer pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = "")String sortBy,
                                                             @RequestParam(name = "orderBy", defaultValue = "asc")String orderBy) throws CustomException {

        return new ResponseEntity<>(new DataResponse(productService.getProductWithPaginationAndSorting(page, pageSize, sortBy, orderBy, searchName).getContent(), HttpStatus.OK), HttpStatus.OK);
    }


    @GetMapping("/sortedByPrice")
    public List<Product> getProductsSortedByPrice() {
        return productService.getProductsSortedByPrice();
    }
}
