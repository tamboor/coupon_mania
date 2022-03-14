package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CouponRepo extends JpaRepository<Coupon,Long> {
 boolean existsByCompanyAndTitle(Company company,String title);
 Set<Coupon> findByCompany (Company company);
 Set<Coupon>findByCompanyAndCategory(Company company,Category category);
 Set<Coupon>findByCompanyAndPrice(Company company, double price);

}
