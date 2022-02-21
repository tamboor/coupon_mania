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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@Order(2)
public class TestCustomerAnnaAref implements CommandLineRunner {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CouponRepo couponRepo;
    @Autowired
    CompanyRepo companyRepo;

    @Override
    public void run(String... args) throws Exception {

        //todo: read hibernate
        customerRepo.save(Customer.builder()
                .email("customer1@customer1.com")
                .password("customer1pass")
                .firstName("nir")
                .lastName("nir")
                .coupon(couponRepo.getById(1))
                .coupon(couponRepo.getById(3))
                .build());
        customerRepo.save(Customer.builder()
                .email("customer2@customer2.com")
                .password("customer2pass")
                .firstName("alon")
                .lastName("alon")

                .build());
        customerRepo.save(Customer.builder()
                .email("customer3@customer3.com")
                .password("customer3pass")
                .firstName("ran")
                .lastName("ran")
                .coupon(couponRepo.getById(4))
                .coupon(couponRepo.getById(5))
                .build());

//deleteCompany(companyRepo.getById(1));

//couponRepo.deleteByTitle(1);

     //   customerRepo.delete(customerRepo.getById(1));

companyRepo.delete(companyRepo.getById(3));
           //  couponRepo.deleteById(4);
        //      companyRepo.deleteById(1);
//        couponRepo.deletePurchaseByCoupon(1);
//        couponRepo.deletePurchaseByCoupon(5);
     //   System.out.println(couponRepo.get(1));


    }
    public void deleteCompany (Company c){
        List<Coupon> couponsToDelete = couponRepo.findAll();
        for (Coupon item : couponsToDelete){
            if (item.getCompany().getId()== c.getId()){
                couponRepo.deleteById(item.getId());
            }
        }
         companyRepo.deleteById(c.getId());
    }
}
