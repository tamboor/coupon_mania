package com.couponmania2.coupon_project.repositories;

import com.couponmania2.coupon_project.beans.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository <Company , Long> {
    Optional<Company> findByEmailAndPassword(String email , String password);
    boolean existsByEmailOrName(String email, String name);
    boolean existsByIdAndName(long id , String name);
}
