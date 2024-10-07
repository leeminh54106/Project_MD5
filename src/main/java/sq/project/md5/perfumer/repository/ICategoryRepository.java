package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.model.entity.Category;


@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByCategoryNameContainsIgnoreCase(String categoryName, Pageable pageable);
    //@Query("select c from Category c where c.categoryName like concat('%',:categoryName,'%')")
    Page<Category> findAllByCategoryNameContains(String categoryName, Pageable pageable);
    boolean existsByCategoryName(String categoryName);
    boolean existsById(Long id);
    Page<Category> findCategoriesByStatusTrue(Pageable pageable);
//    Page<Category> findAllByCategoryNameContains(String name, Pageable pageable);
}
