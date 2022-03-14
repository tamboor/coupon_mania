package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmailAndPassword(String email , String password);
    boolean existsByEmail(String email);
}
