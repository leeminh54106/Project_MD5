package sq.project.md5.perfumer.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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


    @GetMapping("/listBanner")
    public ResponseEntity<DataResponse> getAllBanners( ) {
        return new ResponseEntity<>(new DataResponse(bannerService.getAllBanners(), HttpStatus.OK), HttpStatus.OK);
    }


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
    public ResponseEntity<DataResponse> searchByBannerName(@PageableDefault(page = 0, size = 5, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(value = "search", defaultValue = "") String search) {
        return new ResponseEntity<>(new DataResponse(bannerService.getBannerWithPaginationAndSorting(pageable,search), HttpStatus.OK), HttpStatus.OK);
    }

}
