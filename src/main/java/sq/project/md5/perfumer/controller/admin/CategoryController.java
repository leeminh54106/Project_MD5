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
import sq.project.md5.perfumer.model.entity.Category;
import sq.project.md5.perfumer.service.impl.CategoryServiceImpl;


@RestController
@RequestMapping("/api.example.com/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;


//    @GetMapping
//    public ResponseEntity<DataResponse> getAllCategories(@PageableDefault(page = 0,size = 5, sort = "id",direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "" ) String search) {
//        return new ResponseEntity<>(new DataResponse(categoryService.getAllCategory(pageable,search),HttpStatus.OK),HttpStatus.OK);
//    }

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
    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<DataResponse> changeStatusCategory( @PathVariable("id") Long id) throws CustomException {
        categoryService.changeStatusCategory(id);
        return new ResponseEntity<>(new DataResponse("Đã đổi thành công trạng thái danh mục có mã: "+id, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteCategory(@PathVariable("id") Long id) throws CustomException {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new DataResponse("Đã xóa thành công danh mục có mã: "+id, HttpStatus.OK), HttpStatus.OK);
    }


//    @GetMapping
//    public ResponseEntity<DataResponse> searchByCategoryName(@RequestParam(name = "searchName", defaultValue = "")String searchName,
//                                                             @RequestParam(name = "page", defaultValue = "0")Integer page,
//                                                             @RequestParam(name = "pageSize", defaultValue = "2")Integer pageSize,
//                                                             @RequestParam(name = "sortBy", defaultValue = "")String sortBy,
//                                                             @RequestParam(name = "orderBy", defaultValue = "asc")String orderBy) {
//
//        return new ResponseEntity<>(new DataResponse(categoryService.getCategoryWithPaginationAndSorting(page, pageSize, sortBy, orderBy, searchName),HttpStatus.OK), HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<DataResponse> searchByCategoryName(@PageableDefault(page = 0, size = 3, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable,@RequestParam(value = "search", defaultValue = "") String search) {
        return new ResponseEntity<>(new DataResponse(categoryService.getCategoryWithPaginationAndSorting(pageable, search),HttpStatus.OK), HttpStatus.OK);
    }

}
