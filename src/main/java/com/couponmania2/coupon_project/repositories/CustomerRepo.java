package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
}
