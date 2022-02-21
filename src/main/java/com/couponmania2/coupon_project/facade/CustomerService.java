package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;

import java.util.Set;

public interface CustomerService {


    void purchaseCoupon(Coupon coupon , Customer customer);
    void purchaseCoupon(int couponId , int customerId) ;
    Set<Coupon> getCustomerCoupons(int customerId);
    Set<Coupon> getCustomerCouponsByCategory(Category category);
    Set<Coupon> getCustomerCouponsByMaxPrice(int maxPrice);
    Customer getCustomerDetails(int id);
}
