package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    /**
     * finds a customer by a given email and password
     * @param email customer's email
     * @param password customer's password
     * @return an Optional object that holds a customer
     */
    Optional<Customer> findByEmailAndPassword(String email , String password);
    /**
     * method that finds whether a company exists by a given email
     * @param email email to check
     * @return true if exists, false if not.
     */
    boolean existsByEmail(String email);
    /**
     * finds a customer by a given email
     * @param email customer's email
     * @return an Optional object that holds a customer
     */
    Optional<Customer> findByEmail(String email);
}
