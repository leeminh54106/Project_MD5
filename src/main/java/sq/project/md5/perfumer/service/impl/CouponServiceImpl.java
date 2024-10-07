package sq.project.md5.perfumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sq.project.md5.perfumer.exception.CustomException;
import sq.project.md5.perfumer.model.dto.req.CouponRequest;
import sq.project.md5.perfumer.model.entity.Coupon;
import sq.project.md5.perfumer.repository.ICouponRepository;
import sq.project.md5.perfumer.service.ICouponService;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements ICouponService {
    private final ICouponRepository couponRepository;
    @Override
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại coupon với id: " + id));
    }

    @Override
    public Coupon addCoupon(CouponRequest couponRequest) throws CustomException {
        if (couponRepository.existsByCode(couponRequest.getCode())) {
            throw new CustomException("Tên coupon đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Coupon coupon = Coupon.builder()
                .code(couponRequest.getCode())
                .percent(couponRequest.getPercent())
                .stock(couponRequest.getStock())
                .stardate(couponRequest.getStartdate())
                .enddate(couponRequest.getEnddate())
                .build();
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(CouponRequest couponRequest, Long id) throws CustomException {
        Coupon coupon = couponRepository.findById(id).orElseThrow(() -> new NoSuchElementException("không tồn tại coupon có mã là: " + id));

        if (!coupon.getCode().equals(couponRequest.getCode())
                && couponRepository.existsByCode(couponRequest.getCode())) {
            throw new CustomException("Tên code đã tồn tại", HttpStatus.BAD_REQUEST);
        }

        Coupon coupons = Coupon.builder()
                .id(coupon.getId())
                .code(couponRequest.getCode())
                .percent(couponRequest.getPercent())
                .stock(couponRequest.getStock())
                .stardate(couponRequest.getStartdate())
                .enddate(couponRequest.getEnddate())
                .build();
        return couponRepository.save(coupons);
    }

    @Override
    public void deleteCoupon(Long id) throws CustomException {
        couponRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại coupon: " + id));
        couponRepository.deleteById(id);
    }

    @Override
    public Page<Coupon> getCodeWithPaginationAndSorting(Pageable pageable, String search) {
        Page<Coupon> coupons;
        if(search.isEmpty()){
            coupons = couponRepository.findAll(pageable);
        }else {
            coupons = couponRepository.findAllByCodeContainsIgnoreCase(search,pageable);
        }
        return coupons;
    }
}
