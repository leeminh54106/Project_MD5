package sq.project.md5.perfumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sq.project.md5.perfumer.model.entity.Image;

import java.util.List;

public interface IImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByProductDetailId(Long productDetailId);
}
