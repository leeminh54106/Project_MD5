package sq.project.md5.perfumer.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column( columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    private Boolean status;
}
