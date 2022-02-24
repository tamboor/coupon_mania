package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;

import java.util.Set;

public interface CompanyService {
     void addCoupon(Coupon coupon);
     void updateCoupon(Coupon coupon) throws Exception;
     void deleteCoupon(int couponId);
     Set<Coupon>getAllCompanyCoupons();
     Set<Coupon>getCompanyCouponsByCategory(int companyId,Category category);
    Set<Coupon>getCompanyCouponsByMaxPrice(int companyId,double maxPrice);
    Company getCompanyDetails();


}
