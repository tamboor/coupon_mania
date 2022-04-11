package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Set;

public interface CouponRepo extends JpaRepository<Coupon, Long> {
    /**
     * method that finds whether a coupon exists by a given company and title
     *
     * @param company company to check
     * @param title   title to check
     * @return true if exists, false if not.
     */
    boolean existsByCompanyAndTitle(Company company, String title);

    /**
     * finds coupons by a given company
     *
     * @param company company to check
     * @return an Optional object that holds a set of coupons
     */
    Set<Coupon> findByCompany(Company company);

    /**
     * finds coupons by a given company and category
     *
     * @param company  company to check
     * @param category category to check
     * @return an Optional object that holds a set of coupons
     */
    Set<Coupon> findByCompanyAndCategory(Company company, Category category);

    /**
     * finds coupons by a given company and price
     *
     * @param company company to check
     * @param price   price to check
     * @return an Optional object that holds a set of coupons
     */
    Set<Coupon> findByCompanyAndPriceLessThanEqual(Company company, double price);


//todo: study more about these annotations

    /**
     * deletes coupons whose end date is before the current date
     *
     * @param currDate the current date
     */
    @Transactional
    @Modifying
    void deleteByEndDateBefore(Date currDate);

}
