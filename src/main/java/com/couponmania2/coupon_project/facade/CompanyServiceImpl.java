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

    }

    @Override
    public void updateCoupon(Coupon coupon) {

    }

    @Override
    public void deleteCoupon(int couponId) {

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
