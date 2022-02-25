package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController extends ClientController{
    private final CustomerServiceImpl customerService;
    //todo: check what to do about customer id
        private final int customerId ;

    @Override
    public boolean login(String email, String password) {
        return false;
    }
    @PostMapping("/purchaseCoupon")
    @ResponseStatus(HttpStatus.OK)
    public void purchaseCoupon(@RequestBody Coupon coupon){
        customerService.purchaseCoupon(coupon.getId(),customerId);
    }
    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(){
      return new ResponseEntity<>(customerService.getCustomerCoupons(customerId),HttpStatus.OK)  ;
    }

    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestBody Category category){
        return new ResponseEntity<>(customerService.getCustomerCouponsByCategory(customerId,category),HttpStatus.OK);
    }
    @GetMapping("/getCustomerCoupons/maxPrice")
    public ResponseEntity<?> getCustomerCoupons(@PathVariable double maxPrice){
        return new ResponseEntity<>(customerService.getCustomerCouponsByMaxPrice(customerId,maxPrice),HttpStatus.OK);
    }
    @GetMapping("/getCustomerDetails/?")//:todo check this Get
    public ResponseEntity<?>getCustomerDetails(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.getCustomerDetails(customer.getId()),HttpStatus.OK);
    }


}
