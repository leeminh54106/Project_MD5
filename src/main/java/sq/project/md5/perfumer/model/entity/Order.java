package sq.project.md5.perfumer.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import sq.project.md5.perfumer.constants.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, unique = true, length = 100)
    @Builder.Default
    private String serialNumber = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn( nullable = false)
    private Users users;

    @Column( nullable = false, columnDefinition = "Decimal(10,2)")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column( length = 100)
    private String receiveName;

    private String receiveFullAddress;

    @Column( length = 15)
    private String receivePhone;

    @Column( length = 100)
    private String note;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private Date createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column( nullable = false)
    private Date receivedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
     List<OrderDetails> orderDetails;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
