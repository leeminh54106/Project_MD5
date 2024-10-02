package sq.project.md5.perfumer.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.exception.CustomException;

import sq.project.md5.perfumer.model.dto.req.BrandRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.service.impl.BrandServiceImpl;

@RestController
@RequestMapping("/api.example.com/v1/admin/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandServiceImpl brandService;

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> getBrandById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(brandService.getBrandById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DataResponse> addBrand(@Valid @ModelAttribute BrandRequest brandRequest) throws CustomException {
        return new ResponseEntity<>(new DataResponse(brandService.addBrand(brandRequest), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> updateBrand(@Valid @ModelAttribute BrandRequest brandRequest, @PathVariable("id") Long id) throws CustomException {
        return new ResponseEntity<>(new DataResponse(brandService.updateBrand(brandRequest, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteBrand(@PathVariable("id") Long id) throws CustomException {
        brandService.deleteBrand(id);
        return new ResponseEntity<>(new DataResponse("Đã xóa thành công thương hiệu có mã: "+id, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/searchByBrandName")
    public ResponseEntity<DataResponse> searchByBannerName(@RequestParam(name = "searchName", defaultValue = "")String searchName,
                                                           @RequestParam(name = "page", defaultValue = "0")Integer page,
                                                           @RequestParam(name = "pageSize", defaultValue = "2")Integer pageSize,
                                                           @RequestParam(name = "sortBy", defaultValue = "")String sortBy,
                                                           @RequestParam(name = "orderBy", defaultValue = "asc")String orderBy) {

        return new ResponseEntity<>(new DataResponse(brandService.getBrandWithPaginationAndSorting(page, pageSize, sortBy, orderBy, searchName).getContent(), HttpStatus.OK), HttpStatus.OK);
    }
}
