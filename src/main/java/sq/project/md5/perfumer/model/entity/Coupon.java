package sq.project.md5.perfumer.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "coupon")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, unique = true, length = 100)
    @Builder.Default
    private String code = UUID.randomUUID().toString();;

    @Column( columnDefinition = "TEXT")
    private String percent;

    @Column( nullable = false, columnDefinition = "Decimal(10,2)")
    private Double stock;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private Date stardate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column( nullable = false)
    private Date enddate;

}
