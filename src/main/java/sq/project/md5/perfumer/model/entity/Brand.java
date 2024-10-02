package sq.project.md5.perfumer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "brands")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, unique = true, length = 100)
    private String brandName;

    @Column( columnDefinition = "TEXT")
    private String description;

    private Boolean status;
}
