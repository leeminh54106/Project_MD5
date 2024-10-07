package sq.project.md5.perfumer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sq.project.md5.perfumer.model.entity.Address;
import sq.project.md5.perfumer.model.entity.Users;


import java.util.List;
import java.util.Optional;

@Repository 
public interface IAddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllByUsers(Users users, Pageable pageable);
    Optional<Address> findByIdAndUsers(Long id, Users users);
    boolean existsByPhone(String phone);
    Page<Address>findAllByUsersAndFullAddressContainsIgnoreCase(Users users, String address, Pageable pageable);
    @Query("select a from Address a where a.users = :user and a.isDefault = true")
    Optional<Address> findDefaultAddressByUser(@Param("user") Users user);

}
