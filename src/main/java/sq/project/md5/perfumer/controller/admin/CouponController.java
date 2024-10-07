package sq.project.md5.perfumer.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.CouponRequest;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.service.impl.CouponServiceImpl;


@RestController
@RequestMapping("/api.example.com/v1/admin/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponServiceImpl couponService;

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> getCouponById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new DataResponse(couponService.getCouponById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DataResponse> addCoupon(@Valid @RequestBody CouponRequest couponRequest) throws CustomException {
        return new ResponseEntity<>(new DataResponse(couponService.addCoupon(couponRequest), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse> updateBrand(@Valid @RequestBody CouponRequest couponRequest, @PathVariable("id") Long id) throws CustomException {
        return new ResponseEntity<>(new DataResponse(couponService.updateCoupon(couponRequest, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse> deleteCoupon(@PathVariable("id") Long id) throws CustomException {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>(new DataResponse("Đã xóa thành công coupon có mã: "+id, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<DataResponse> searchByCouponCode(@PageableDefault(page = 0, size = 3, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(value = "search", defaultValue = "") String search) {
        return new ResponseEntity<>(new DataResponse(couponService.getCodeWithPaginationAndSorting(pageable, search),HttpStatus.OK), HttpStatus.OK);
    }
}


