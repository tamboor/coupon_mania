package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;

import java.util.Set;

public class CustomerFacade implements CustomerService{

    @Override
    public void purchaseCoupon(Coupon coupon, Customer customer) {

    }

    @Override
    public void purchaseCoupon(int couponId, int customerId) {

    }

    @Override
    public Set<Coupon> getCustomerCoupons(int customerId) {
        return null;
    }

    @Override
    public Set<Coupon> getCustomerCouponsByCategory(Category category) {
        return null;
    }

    @Override
    public Set<Coupon> getCustomerCouponsByMaxPrice(int maxPrice) {
        return null;
    }

    @Override
    public Customer getCustomerDetails(int id) {
        return null;
    }
}
