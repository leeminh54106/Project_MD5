package sq.project.md5.perfumer.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopping_cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

}
