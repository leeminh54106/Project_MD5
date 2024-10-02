package sq.project.md5.perfumer.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;


import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, unique = true, length = 100)
    @Builder.Default
    private String sku= UUID.randomUUID().toString();

    @Column( nullable = false, unique = true, length = 100)
    private String productName;

    @Column( columnDefinition = "TEXT")
    private String description;
    @Column( columnDefinition = "TEXT")
    private String guarantee;
    @Column( columnDefinition = "TEXT")
    private String instruct;

    @Column( length = 255)
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

//    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

//    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    private Boolean status;
}
