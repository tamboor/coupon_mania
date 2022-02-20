package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository <Company , Integer> {
}
