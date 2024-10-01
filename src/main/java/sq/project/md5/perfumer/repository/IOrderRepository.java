package sq.project.md5.perfumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.constants.OrderStatus;
import sq.project.md5.perfumer.model.entity.Order;
import sq.project.md5.perfumer.model.entity.Users;


import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUsers(Users users);
    Optional<Order> findById(Long id);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByStatusAndUsers(OrderStatus orderStatus, Users users);
    Optional<Order> findBySerialNumberAndUsers(String serialNumber, Users users);
    Optional<Order> findByIdAndUsers(Long id, Users users);


}
