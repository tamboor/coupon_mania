package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.beans.*;
import com.couponmania2.coupon_project.facade.CustomerFacade;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order (1)
public class TestMockData implements CommandLineRunner {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private PurchaseRepo purchaseRepo;
    @Autowired
    private CustomerFacade customerFacade;


    @Override
    public void run(String... args) throws Exception {
        companyRepo.save(new Company("company1" , "email1" , "password1"));
        companyRepo.save(new Company("company2" , "email2" , "password2"));
        companyRepo.save(new Company("company3" , "email3" , "password3"));
        companyRepo.save(new Company("company4" , "email4" , "password4"));
        companyRepo.save(new Company("company5" , "email5" , "password5"));


        couponRepo.save(new Coupon(companyRepo.getById(1),Category.Cars , "coupon1",
                "coupon1desc" ,Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)) ,
                10 , 10 ,"img"));
        couponRepo.save(new Coupon(companyRepo.getById(1),Category.Tattoos , "coupon2",
                "coupon2desc" ,Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)) ,
                10 , 15 ,"img"));
        couponRepo.save(new Coupon(companyRepo.getById(1),Category.Food , "coupon3",
                "coupon3desc" ,Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)) ,
                10 , 20 ,"img"));
        couponRepo.save(new Coupon(companyRepo.getById(2),Category.Xtreme , "coupon4",
                "coupon4desc" ,Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)) ,
                10 , 25 ,"img"));
        couponRepo.save(new Coupon(companyRepo.getById(2),Category.Vacation , "coupon5",
                "coupon5desc" ,Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)) ,
                10 , 30 ,"img"));
        couponRepo.save(new Coupon(companyRepo.getById(3),Category.Food , "coupon6",
                "coupon6desc" ,Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)) ,
                10 , 45 ,"img"));
        couponRepo.save(new Coupon(companyRepo.getById(3),Category.Vacation , "coupon7",
                "coupon7desc" ,Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)) ,
                10 , 50 ,"img"));

//todo: find out what happens if you insert purchases in a new customer that doesn't has an id yet
        customerRepo.save(new Customer("nir" , "katz" , "mail1" , "pass"));
        customerRepo.save(new Customer("alon" , "mintz" , "mail2" , "pass"));
        customerRepo.save(new Customer("ran" , "manor" , "mail3" , "pass"));


        purchaseRepo.save(new Purchase(customerRepo.getById(1) , couponRepo.getById(3)));
        purchaseRepo.save(new Purchase(customerRepo.getById(1) , couponRepo.getById(4)));
        purchaseRepo.save(new Purchase(customerRepo.getById(2) , couponRepo.getById(5)));
        purchaseRepo.save(new Purchase(customerRepo.getById(2) , couponRepo.getById(6)));
        purchaseRepo.save(new Purchase(customerRepo.getById(3) , couponRepo.getById(3)));
        purchaseRepo.save(new Purchase(customerRepo.getById(3) , couponRepo.getById(5)));


//        customerRepo.deleteById(1);
//        couponRepo.deleteById(5);
//        companyRepo.deleteById(3);
        Company insertCompany = new Company("company 4", "mail4", "pass4");

                insertCompany.setCoupons(new HashSet<Coupon>(Arrays.asList(
                new Coupon(insertCompany , Category.Cars , "cool title1" ,
                        "desccccc1" , Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)),
                        10 , 10 ,"img"
                        ),
                new Coupon(insertCompany , Category.Vacation , "cool title2" ,
                        "desccccc2" , Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)),
                        10 , 10 ,"img"
                ),
                new Coupon(insertCompany , Category.Cars , "cool title3" ,
                        "desccccc3" , Date.valueOf(LocalDate.now()) , Date.valueOf(LocalDate.now().plusDays(14)),
                        -1 , 10 ,"img"
                ))));
        companyRepo.save(insertCompany);
//        System.out.println(couponRepo.getById(1));
//        purchaseRepo.deleteById(4);

    //    customerFacade.purchaseCoupon(couponRepo.getById(8), customerRepo.getById(3));
     //   customerFacade.getCustomerCoupons(1);
      //  System.out.println(purchaseRepo.getCouponsOfCustomerByMaxPrice(customerRepo.getById(3),21));

companyRepo.deleteById(1);







    }
}
