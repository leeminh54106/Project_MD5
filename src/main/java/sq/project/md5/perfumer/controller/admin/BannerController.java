package sq.project.md5.perfumer.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.BannerRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.service.impl.BannerServiceImpl;

@RestController
@RequestMapping("/api.example.com/v1/admin/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerServiceImpl bannerService;
//
//    @GetMapping
//    public ResponseEntity<DataResponse> getAllBanners( ) {
//        return new ResponseEntity<>(new DataResponse(bannerService.getAllBanners(), HttpStatus.OK), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> getBannerById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(bannerService.getBannerById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DataResponse> addBanner(@Valid @ModelAttribute BannerRequest bannerRequest) throws CustomException {
        return new ResponseEntity<>(new DataResponse(bannerService.addBanner(bannerRequest), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> updateBanner(@Valid @ModelAttribute BannerRequest bannerRequest, @PathVariable("id") Long id) throws CustomException {
        return new ResponseEntity<>(new DataResponse(bannerService.updateBanner(bannerRequest, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteBanner(@PathVariable("id") Long id) throws CustomException {
        bannerService.deleteBanner(id);
        return new ResponseEntity<>(new DataResponse("Đã xóa thành công banner có mã: "+id, HttpStatus.OK), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<DataResponse> searchByBannerName(@RequestParam(name = "searchName", defaultValue = "")String searchName,
                                                             @RequestParam(name = "page", defaultValue = "0")Integer page,
                                                             @RequestParam(name = "pageSize", defaultValue = "2")Integer pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = "")String sortBy,
                                                             @RequestParam(name = "orderBy", defaultValue = "asc")String orderBy) {

        return new ResponseEntity<>(new DataResponse(bannerService.getBannerWithPaginationAndSorting(page, pageSize, sortBy, orderBy, searchName), HttpStatus.OK), HttpStatus.OK);
    }

}
