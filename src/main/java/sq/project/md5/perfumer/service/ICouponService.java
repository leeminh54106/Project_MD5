package sq.project.md5.perfumer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.CouponRequest;
import sq.project.md5.perfumer.model.entity.Coupon;

public interface ICouponService {
    Coupon getCouponById(Long id);
    Coupon addCoupon(CouponRequest couponRequest) throws CustomException;
    Coupon updateCoupon(CouponRequest couponRequest, Long id) throws CustomException;
    void deleteCoupon(Long id) throws CustomException;
    Page<Coupon> getCodeWithPaginationAndSorting(Pageable pageable, String search);
}
