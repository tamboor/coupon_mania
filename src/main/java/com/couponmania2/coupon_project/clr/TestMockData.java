package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.beans.*;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import com.couponmania2.coupon_project.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


//todo: beautify mock data
@Component
@Order(1)
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
    private CustomerServiceImpl customerFacade;

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    CustomerServiceImpl customerService;


    @Override
    public void run(String... args) throws Exception {


        companyRepo.save(new Company("company1", "email1", "password1"));
        companyRepo.save(new Company("company2", "email2", "password2"));
        companyRepo.save(new Company("company3", "email3", "password3"));
        companyRepo.save(new Company("company4", "email4", "password4"));
        companyRepo.save(new Company("company5", "email5", "password5"));


        couponRepo.save(new Coupon(companyRepo.getById(1L), Category.cars, "coupon1",
                "company1car", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 10, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(1L), Category.tattoos, "coupon2",
                "company1tattoo", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 15, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(1L), Category.food, "coupon3",
                "company1food", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 20, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(2L), Category.xtreme, "coupon4",
                "company2xtreme", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 25, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(2L), Category.vacation, "coupon5",
                "company2vacation", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 30, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(3L), Category.food, "coupon6",
                "company3food", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 45, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(3L), Category.vacation, "coupon7",
                "company3vacation", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 50, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(4L), Category.cars, "coupon8",
                "company4car", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 50, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(4L), Category.xtreme, "coupon9",
                "company4xtreme", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 50, "img"));
        couponRepo.save(new Coupon(companyRepo.getById(5L), Category.tattoos, "coupon10",
                "company5tattoo", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                10, 50, "img"));

        customerRepo.save(new Customer("nir", "katz", "nir@katz", "nirpass"));
        customerRepo.save(new Customer("alon", "mintz", "alon@mintz", "alonpass"));
        customerRepo.save(new Customer("ran", "manor", "ran@manor", "ranpass"));

        purchaseRepo.save(new Purchase(customerRepo.getById(1L), couponRepo.getById(3L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(1L), couponRepo.getById(4L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(2L), couponRepo.getById(5L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(2L), couponRepo.getById(6L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(3L), couponRepo.getById(3L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(3L), couponRepo.getById(5L)));



    }
}
