package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;

import java.util.Set;

public interface CompanyService {
     void addCoupon(Coupon coupon);
     void updateCoupon(Coupon coupon) throws Exception;
     void deleteCoupon(long couponId);
     Set<Coupon>getAllCompanyCoupons(long companyId);
     Set<Coupon>getCompanyCouponsByCategory(long companyId,Category category);
    Set<Coupon>getCompanyCouponsByMaxPrice(long companyId,double maxPrice);
    Company getCompanyDetails(long companyId);


}
