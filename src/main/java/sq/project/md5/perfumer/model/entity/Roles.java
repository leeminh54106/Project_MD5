package sq.project.md5.perfumer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import sq.project.md5.perfumer.constants.RoleName;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) //Chuyển đổi về chuỗi
    private RoleName roleName;
}
