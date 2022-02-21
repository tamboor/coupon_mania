package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepo extends JpaRepository <Company , Integer> {

    @Override
    @Transactional
    @Modifying
    default void deleteById(Integer integer) {

    }
}
