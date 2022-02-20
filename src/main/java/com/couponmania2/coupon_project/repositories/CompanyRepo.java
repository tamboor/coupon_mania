package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepo extends JpaRepository <Company , Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Company c WHERE c.id = ?1")

    void deleteById(Integer id);

}
