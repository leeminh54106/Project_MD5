package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.CategoryRequest;
import sq.project.md5.perfumer.model.entity.Category;
import sq.project.md5.perfumer.repository.ICategoryRepository;
import sq.project.md5.perfumer.repository.IProductRepository;
import sq.project.md5.perfumer.service.ICategoryService;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    private final IProductRepository productRepository;

    @Override
    public List<Category> getAllCategory() {
       List<Category> categories = categoryRepository.findAll();

        return categories;
    }



//    @Override
//    public List<Category> getAllCategories() {
//        List<Category> categories = categoryRepository.findAll();
//
//        if (categories.isEmpty()) {
//            throw new NoSuchElementException("Không có danh mục nào.");
//        }
//
//        return categories;
//    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại danh mục với id: " + id));

    }

    @Override
    public Category addCategory(CategoryRequest category) throws CustomException {
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new CustomException("Tên danh mục đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Category cate = Category.builder()
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .status(category.getStatus())
                .build();
        return categoryRepository.save(cate);
    }

    @Override
    public Category updateCategory(CategoryRequest category, Long id) throws CustomException {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Khong ton tai danh muc nao co ma: " + id));

        if (!existingCategory.getCategoryName().equals(category.getCategoryName())
                && categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new CustomException("Tên danh mục đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Category cate = Category.builder()
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .status(category.getStatus())
                .build();
        cate.setId(id);
        return categoryRepository.save(cate);
    }

    @Override
    public void deleteCategory(Long id){
        categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Khong ton tai danh mục: " + id));

        if(productRepository.existsByCategory_Id(id)) {
            throw new NoSuchElementException("Không thể xóa danh mục vì danh mục có sản phẩm");
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public Page<Category> getCategoryWithPaginationAndSorting(Pageable pageable, String search)  {
        Page<Category> categories;
        if(search.isEmpty()){
            categories = categoryRepository.findAll(pageable);
        }else {
            categories = categoryRepository.findAllByCategoryNameContains(search,pageable);
        }
        return categories;


    }
//    // Tìm danh mục
//    Page<Category> categoriesPage;
//        if (searchName.isEmpty()) {
//        categoriesPage = categoryRepository.findAll(pageable);
//    } else {
//        categoriesPage = categoryRepository.findAllByCategoryNameContains(searchName, pageable);
//    }
//
//    // Kiểm tra nếu không có danh mục
//        if (categoriesPage.isEmpty()) {
//        throw new NoSuchElementException("Không tìm thấy danh mục");
//    }
//
//        return categoriesPage;

    @Override
    public Page<Category> listCategoriesForSale(Pageable pageable) {
        return categoryRepository.findCategoriesByStatusTrue(pageable);
    }

    @Override
    public void changeStatusCategory(Long categoryId) throws CustomException {
       Category existingCategory =  categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException("Không tồn tại danh mục : " + categoryId));
        existingCategory.setStatus(!existingCategory.getStatus());
        categoryRepository.save(existingCategory);
    }
}
