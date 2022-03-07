package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public interface CustomerService {

    void purchaseCoupon(Coupon coupon , Customer customer);
    void purchaseCoupon(long couponId , long customerId) ;
    Set<Coupon> getCustomerCoupons(long customerId);
    Set<Coupon> getCustomerCouponsByCategory(long customerId, Category category);
    Set<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice);
    Customer getCustomerDetails(long customerId);
}
