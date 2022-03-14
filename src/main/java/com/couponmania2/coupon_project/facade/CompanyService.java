package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;

import java.util.Set;

public interface CompanyService {
    Company checkCredentials(String email, String password, ClientType clientType) throws AppUnauthorizedRequestException;
     void addCoupon(Coupon coupon) throws AppTargetExistsException;
     void updateCoupon(Coupon coupon) throws Exception;
     void deleteCoupon(long couponId , long companyId) throws AppTargetNotFoundException, AppInvalidInputException;
     Set<Coupon>getAllCompanyCoupons(long companyId) throws AppTargetNotFoundException;
     Set<Coupon>getCompanyCouponsByCategory(long companyId,Category category) throws AppTargetNotFoundException;
    Set<Coupon>getCompanyCouponsByMaxPrice(long companyId,double maxPrice) throws AppTargetNotFoundException, AppInvalidInputException;
    Company getCompanyDetails(long companyId) throws AppTargetNotFoundException;


}
