package sq.project.md5.perfumer.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "product_detail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, columnDefinition = "Decimal(10,2)")
    private Double unitPrice;

    @Column( length = 255)
    private String image;

    @Column( nullable = false)
    private Integer stockQuantity;

    @Column( nullable = false)
    private Long volume;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
