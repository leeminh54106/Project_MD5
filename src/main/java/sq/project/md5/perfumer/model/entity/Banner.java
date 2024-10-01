package sq.project.md5.perfumer.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, unique = true, length = 100)
    private String bannerName;

    @Column( length = 255)
    private String urlImage;
}
