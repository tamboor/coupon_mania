package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface CouponRepo extends JpaRepository<Coupon,Integer> {
 boolean existsByCompanyAndTitle(Company company,String title);
 Set<Coupon>findByCompanyAndCategory(Company company,Category category);
 Set<Coupon>findByCompanyAndPrice(Company company, double price);

}
