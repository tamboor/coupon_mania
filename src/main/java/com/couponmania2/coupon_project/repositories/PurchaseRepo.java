package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.beans.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PurchaseRepo extends JpaRepository<Purchase , Long> {
    Purchase findByCustomerAndCoupon (Customer customer , Coupon coupon);

    @Query("SELECT p.coupon FROM Purchase p WHERE p.customer = :customer")
    Set<Coupon> getAllCouponsOfCustomer(@Param("customer") Customer customer);

    @Query("SELECT p.coupon FROM Purchase p WHERE p.customer = :customer AND p.coupon.category = :category")
    Set<Coupon> getCouponsOfCustomerByCategory(@Param("customer") Customer customer,@Param("category") Category category);

    @Query("SELECT p.coupon FROM Purchase p WHERE p.customer = :customer AND p.coupon.price <= :price")
    Set<Coupon> getCouponsOfCustomerByMaxPrice(@Param("customer") Customer customer,@Param("price") double price);
}
