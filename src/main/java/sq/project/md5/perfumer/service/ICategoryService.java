package sq.project.md5.perfumer.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.CategoryRequest;
import sq.project.md5.perfumer.model.entity.Category;


import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category addCategory(CategoryRequest category) throws CustomException;
    Category updateCategory(CategoryRequest category, Long id) throws CustomException;
    void deleteCategory(Long id) throws CustomException;
    Page<Category> getCategoryWithPaginationAndSorting(Integer page, Integer pageSize, String sortBy, String orderBy, String searchName);
    Page<Category> listCategoriesForSale(Pageable pageable);
}
