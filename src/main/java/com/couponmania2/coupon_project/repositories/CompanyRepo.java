package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository <Company , Long> {
    /**
     * finds a company by a given email and password
     * @param email company's email
     * @param password company's password
     * @return an Optional object that holds a company
     */
    Optional<Company> findByEmailAndPassword(String email , String password);

    /**
     * method that finds whether a company exists by a given email or name
     * @param email email to check
     * @param name name to check
     * @return true if exists, false if not.
     */
    boolean existsByEmailOrName(String email, String name);
    /**
     * method that finds whether a company exists by a given id and name
     * @param id id to check
     * @param name name to check
     * @return true if exists, false if not.
     */
    boolean existsByIdAndName(long id , String name);
    /**
     * finds a company by a given email
     * @param email company's email
     * @return an Optional object that holds a company
     */
    Optional<Company> findByEmail(String email);
}
