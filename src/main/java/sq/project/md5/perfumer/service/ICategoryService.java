package sq.project.md5.perfumer.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.CategoryRequest;
import sq.project.md5.perfumer.model.entity.Category;
import sq.project.md5.perfumer.model.entity.Product;


import java.util.List;

public interface ICategoryService {
    List<Category> findAllNoPagination();
    Page<Category> getAllCategory(Pageable pageable, String search);
    Category getCategoryById(Long id);
    Category addCategory(CategoryRequest category) throws CustomException;
    Category updateCategory(CategoryRequest category, Long id) throws CustomException;
    void deleteCategory(Long id) throws CustomException;
    Page<Category> getCategoryWithPaginationAndSorting(Pageable pageable,String search);
    Page<Category> listCategoriesForSale(Pageable pageable);
    void changeStatusCategory(Long categoryId) throws CustomException;
}
