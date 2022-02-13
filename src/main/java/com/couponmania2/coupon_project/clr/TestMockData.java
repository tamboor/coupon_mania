package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order (1)
public class TestMockData implements CommandLineRunner {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CouponRepo couponRepo;


    @Override
    public void run(String... args) throws Exception {
       companyRepo.save(Company.builder()
                       .email("company1@email.com")
                       .name("company1")
                       .password("company1")
               .build());


    }
}
