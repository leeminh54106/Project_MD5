package sq.project.md5.perfumer.controller.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.WishListRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.model.dto.resp.WishListResponse;
import sq.project.md5.perfumer.service.IWishListService;


import java.util.List;

@RestController
@RequestMapping("/api.example.com/v1/user")
@RequiredArgsConstructor
public class WishListController {

    private final IWishListService wishListService;

    @PostMapping("/wishList")
    public ResponseEntity<DataResponse> addWishList(@RequestBody WishListRequest wishListRequest) throws CustomException {
        WishListResponse wishListResponse = wishListService.addWishList(wishListRequest);
        return new ResponseEntity<>(new DataResponse(wishListResponse, HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @GetMapping("/wishList")
    public ResponseEntity<DataResponse> getWishList() throws CustomException {
        List<WishListResponse> wishListResponses = wishListService.getAllWishList();
        return new ResponseEntity<>(new DataResponse(wishListResponses, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/wishList/{id}")
    public ResponseEntity<DataResponse> deleteWishList(@PathVariable Long id) throws CustomException {
         wishListService.deleteWishList(id);
        return new ResponseEntity<>(new DataResponse("Xóa thành công sản phẩm yêu thích", HttpStatus.OK), HttpStatus.OK);
    }
}
