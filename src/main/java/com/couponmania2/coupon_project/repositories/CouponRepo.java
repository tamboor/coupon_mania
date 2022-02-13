package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepo extends JpaRepository<Coupon,Long> {
}
