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
import sq.project.md5.perfumer.model.dto.req.CategoryRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.service.impl.CategoryServiceImpl;


@RestController
@RequestMapping("/api.example.com/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;


    @GetMapping
    public ResponseEntity<DataResponse> getAllCategories(@PageableDefault(page = 0,size = 5, sort = "id",direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(defaultValue = "" ) String search) {
        return new ResponseEntity<>(new DataResponse(categoryService.getAllCategory(pageable,search),HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> getCategoryById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(categoryService.getCategoryById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DataResponse> addCategory(@Valid @RequestBody CategoryRequest category) throws CustomException {
        return new ResponseEntity<>(new DataResponse(categoryService.addCategory(category), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> updateCategory(@Valid @RequestBody CategoryRequest category, @PathVariable("id") Long id) throws CustomException {
        return new ResponseEntity<>(new DataResponse(categoryService.updateCategory(category, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteCategory(@PathVariable("id") Long id) throws CustomException {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new DataResponse("Đã xóa thành công danh mục có mã: "+id, HttpStatus.OK), HttpStatus.OK);
    }



}
