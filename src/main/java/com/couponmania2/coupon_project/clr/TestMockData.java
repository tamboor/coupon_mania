package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;

//@Component
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

        companyRepo.save(Company.builder()
                .email("company1@email.com")
                .name("company2")
                .password("company2")
                .build());

        companyRepo.save(Company.builder()
                .email("company1@email.com")
                .name("company3")
                .password("company3")
                .build());


        couponRepo.save(Coupon.builder()
                        .amount(10)
                        .category(Category.Cars)
                        .endDate(new Date())
                        .description("description")
                        .company(companyRepo.getById(1))
                        .title("coupon1")
                        .image("url")
                        .price(20)
                        .startDate(new Date())
                .build());

        couponRepo.save(Coupon.builder()
                .amount(10)
                .category(Category.Food)
                .endDate(new Date())
                .description("description")
                .company(companyRepo.getById(2))
                .title("coupon2")
                .image("url")
                .price(20)
                .startDate(new Date())
                .build());
        couponRepo.save(Coupon.builder()
                .amount(12)
                .category(Category.Food)
                .endDate(new Date())
                .description("description")
                .company(companyRepo.getById(2))
                .title("coupon3")
                .image("url")
                .price(15)
                .startDate(new Date())
                .build());
        couponRepo.save(Coupon.builder()
                .amount(18)
                .category(Category.Tattoos)
                .endDate(new Date())
                .description("description")
                .company(companyRepo.getById(3))
                .title("coupon4")
                .image("url")
                .price(15)
                .startDate(new Date())
                .build());
        couponRepo.save(Coupon.builder()
                .amount(18)
                .category(Category.Tattoos)
                .endDate(new Date())
                .description("description")
                .company(companyRepo.getById(1))
                .title("coupon5")
                .image("url")
                .price(25)
                .startDate(new Date())
                .build());



//        customerRepo.save(Customer.builder()
//                        .email("customer1@customer1.com")
//                        .password("customer1pass")
//                        .firstName("nir")
//                        .lastName("nir")
////                        .coupon(couponRepo.getById((long)1))
////                        .coupon(couponRepo.getById((long)2))
//                .build());
//        customerRepo.save(Customer.builder()
//                .email("customer2@customer2.com")
//                .password("customer2pass")
//                .firstName("alon")
//                .lastName("alon")
//
//                .build());
//        customerRepo.save(Customer.builder()
//                .email("customer3@customer3.com")
//                .password("customer3pass")
//                .firstName("ran")
//                .lastName("ran")
//
//                .build());





    }
}
