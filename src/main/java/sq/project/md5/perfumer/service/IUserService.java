package sq.project.md5.perfumer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.UserRequest;
import sq.project.md5.perfumer.model.entity.Users;


import java.util.List;

public interface IUserService {
    Page<Users> getAllUsers(Pageable pageable, String search);
    Users getUserById(Long id);
    Users getUserByUserName(String username);
    Users updateUserStatus(Long id, Boolean status) throws CustomException;
    Page<Users> getUsersWithPaginationAndSorting(Integer page, Integer pageSize, String sortBy, String orderBy, String searchName);
    boolean changePassword(String oldPassword, String newPassword, String confirmNewPassword);
    Users getCurrentLoggedInUser();

    Users updateUser(UserRequest userRequest);
}
