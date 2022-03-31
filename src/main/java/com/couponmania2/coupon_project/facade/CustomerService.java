package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public interface CustomerService {

    Customer checkCredentials(String userName, String userPass, ClientType clientType) throws AppUnauthorizedRequestException;
    void purchaseCoupon(Coupon coupon , Customer customer) throws AppTargetExistsException;
    void purchaseCoupon(long couponId , long customerId) throws AppTargetExistsException, AppTargetNotFoundException;
    Set<Coupon> getCustomerCoupons(long customerId) throws AppTargetNotFoundException;
    Set<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws AppTargetNotFoundException;
    Set<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws AppTargetNotFoundException, AppInvalidInputException;
    Customer getCustomerDetails(long customerId) throws AppTargetNotFoundException;


}
