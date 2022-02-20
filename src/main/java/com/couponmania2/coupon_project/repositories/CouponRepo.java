package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponRepo extends JpaRepository<Coupon,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Coupon c WHERE c.id = ?1")
    void deleteById(Integer id);

    @Modifying
    @Transactional
    @Query("SELECT c FROM Coupon c JOIN c.owners o WHERE c.id = ?1")
    List<Coupon> get(Integer id);

    //todo: add a delete purchase
}
