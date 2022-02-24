package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CompanyServiceImpl implements CompanyService {
    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CustomerRepo customerRepo;
    @Autowired
    protected CouponRepo couponRepo;
    @Autowired
    protected PurchaseRepo purchaseRepo;

    @Override
    public void addCoupon(Coupon coupon) {
        if (!couponRepo.existsByCompanyAndTitle(coupon.getCompany(), coupon.getTitle())) {
            couponRepo.save(coupon);
        } else {
            //todo: throw add custom  exp
        }
    }

    //todo: check if company verification can be implemented in REST
    @Override
    public void updateCoupon(Coupon coupon) throws Exception {
        if (couponRepo.getById(coupon.getId()).getCompany() != coupon.getCompany()) {
            throw new Exception("CCCCCCCCC");
        }
        couponRepo.save(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) {
        if (!couponRepo.existsById(couponId)) {
            //:todo throw exp coupon exist
        }
        couponRepo.deleteById(couponId);


    }

    @Override
    public Set<Coupon> getAllCompanyCoupons() {
        return null;
    }

    @Override
    public Set<Coupon> getCompanyByCategory(Category category) {
        return null;
    }

    @Override
    public Set<Coupon> getCompanyByMaxPrice(double maxPrice) {
        return null;
    }

    @Override
    public Company getCompanyDetails() {
        return null;
    }
}
