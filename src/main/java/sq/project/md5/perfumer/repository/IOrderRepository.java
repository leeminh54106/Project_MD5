package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.constants.OrderStatus;
import sq.project.md5.perfumer.model.entity.Order;
import sq.project.md5.perfumer.model.entity.Users;


import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUsers(Users user, Pageable pageable);
//    List<Order> findAllByUsers(Users users);
    Optional<Order> findById(Long id);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    Page<Order> findByStatusAndUsers(OrderStatus orderStatus, Users user, Pageable pageable);
    Optional<Order> findBySerialNumberAndUsers(String serialNumber, Users users);
    Optional<Order> findByIdAndUsers(Long id, Users users);
    Page<Order> findAllByUsersAndStatus(Users user, OrderStatus status, Pageable pageable);
    Page<Order> findAllByUsersUsernameContainsIgnoreCase(String search, Pageable pageable);

}
