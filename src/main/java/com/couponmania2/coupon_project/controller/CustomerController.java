package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor

//todo: add jwt and update methods accordingly.
public class CustomerController extends ClientController{
    private final CustomerServiceImpl customerService;
//    private final int customerId;
    //todo: check what to do about customer id
//    @Autowired
//    UserDetails userCustomerDetails;


    @Override
    public boolean login(String email, String password) {
        return false;
    }

    @PostMapping("/newPurchase")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void purchaseCoupon(@RequestBody Coupon coupon){
      //  customerService.purchaseCoupon(customerService.getCustomerDetails(userCustomerDetails.getId()),coupon.getId());
    }
//
//    @GetMapping("/getCustomerCoupons")
//    public ResponseEntity<?> getCustomerCoupons(){
//      return new ResponseEntity<>(customerService.getCustomerCoupons(customerId),HttpStatus.OK)  ;
//    }
//
//    @GetMapping("/getCustomerCoupons/category")
//    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestBody Category category){
//        return new ResponseEntity<>(customerService.getCustomerCouponsByCategory(customerId,category),HttpStatus.OK);
//    }
//
//    @GetMapping("/getCustomerCoupons/maxPrice")
//    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@PathVariable double maxPrice){
//        return new ResponseEntity<>(customerService.getCustomerCouponsByMaxPrice(customerId,maxPrice),HttpStatus.OK);
//    }

    @GetMapping("/getCustomerDetails/?")//:todo check this Get
    public ResponseEntity<?>getCustomerDetails(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.getCustomerDetails(customer.getId()),HttpStatus.OK);
    }


}
