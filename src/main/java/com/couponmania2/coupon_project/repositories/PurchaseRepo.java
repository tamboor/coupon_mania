package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.beans.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PurchaseRepo extends JpaRepository<Purchase, Long> {
    /**
     * finds a purchase by a given customer and coupon
     * @param customer customer to check
     * @param coupon coupon to check
     * @return an Optional object that holds a purchase
     */
    Optional<Purchase> findByCustomerAndCoupon(Customer customer, Coupon coupon);

    /**
     * get all coupons owned by a specific customer
     * @param customer the customer to check it's coupons
     * @return set of coupons owned by the customer
     */
    @Query("SELECT p.coupon FROM Purchase p WHERE p.customer = :customer")
    Set<Coupon> getAllCouponsOfCustomer(@Param("customer") Customer customer);

    /**
     * get coupons owned by a specific customer by a specific category
     * @param customer the customer to check its coupons
     * @param category category to check
     * @return set of a specific category coupons owned by the customer
     */
    @Query("SELECT p.coupon FROM Purchase p WHERE p.customer = :customer AND p.coupon.category = :category")
    Set<Coupon> getCouponsOfCustomerByCategory(@Param("customer") Customer customer, @Param("category") Category category);
    /**
     * get coupons owned by a specific customer under a given price
     * @param customer the customer to check its coupons
     * @param price max price
     * @return set of coupons under the given price owned by the customer
     */
    @Query("SELECT p.coupon FROM Purchase p WHERE p.customer = :customer AND p.coupon.price <= :price")
    Set<Coupon> getCouponsOfCustomerByMaxPrice(@Param("customer") Customer customer, @Param("price") double price);
}
