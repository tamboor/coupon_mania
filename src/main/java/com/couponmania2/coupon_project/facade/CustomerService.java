package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public interface CustomerService {

    void purchaseCoupon(Coupon coupon , Customer customer);
    void purchaseCoupon(int couponId , int customerId) ;
    Set<Coupon> getCustomerCoupons(int customerId);
    Set<Coupon> getCustomerCouponsByCategory(int customerId, Category category);
    Set<Coupon> getCustomerCouponsByMaxPrice(int customerId, double maxPrice);
    Customer getCustomerDetails(int customerId);
}
