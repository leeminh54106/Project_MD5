package sq.project.md5.perfumer.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column( length = 255)
    private String image;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}
