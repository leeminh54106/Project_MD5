package sq.project.md5.perfumer.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.model.entity.Users;
import sq.project.md5.perfumer.service.IRoleService;
import sq.project.md5.perfumer.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api.example.com/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IUserService userService;

    private final IRoleService roleService;

    @GetMapping
    public ResponseEntity<DataResponse> admin() {
        return new ResponseEntity<>(new DataResponse("Chào mừng đến với trang quản trị", HttpStatus.OK), HttpStatus.OK);
    }


    @GetMapping("/userAdmin")
    public ResponseEntity<DataResponse> getAllUserAdmin(@PageableDefault(page = 0,size = 5, sort = "id",direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "" ) String search, @RequestParam(defaultValue = "") String SortOptions) {
        return new ResponseEntity<>(new DataResponse(userService.getAllUsers(pageable, search, SortOptions), HttpStatus.OK), HttpStatus.OK);
    }


    @GetMapping("/searchByName")
    public ResponseEntity<DataResponse> searchUserByName(@PageableDefault(page = 0, size = 5, sort = "id",
    direction = Sort.Direction.ASC)Pageable pageable,@RequestParam (value = "search",defaultValue = "") String search){
        return new ResponseEntity<>(new DataResponse(userService.getUsersWithPaginationAndSorting(pageable,search),HttpStatus.OK),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> changeUserStatus(@PathVariable Long id, @RequestParam Boolean status) throws CustomException {
        Users changeUserStatus = userService.updateUserStatus(id, status);
        return new ResponseEntity<>(new DataResponse(changeUserStatus, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<DataResponse> getAll(){
        return new ResponseEntity<>(new DataResponse(roleService.getAllRoles(), HttpStatus.OK), HttpStatus.OK);
    }
}
