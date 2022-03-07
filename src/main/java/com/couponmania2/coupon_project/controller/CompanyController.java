package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.facade.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor

//todo: add jwt and update methods accordingly.
public class CompanyController extends ClientController{

    private final CompanyServiceImpl companyService;
    //temp
//    private final int companyId;

    @Override
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        return null;
    }


    @PostMapping("/addCoupon")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon (@RequestBody Coupon coupon){
        companyService.addCoupon(coupon);
    }

    @PutMapping("/updateCoupon")
    @ResponseStatus(HttpStatus.OK)
    private void updateCoupon (@RequestBody Coupon coupon) throws Exception {
        companyService.updateCoupon(coupon);
    }


//    @DeleteMapping("/deleteCoupon/{couponId}")
//    @ResponseStatus(HttpStatus.OK)
//    private void deleteCoupon (@PathVariable long couponId){
//        companyService.deleteCoupon(couponId);
//    }

//    @GetMapping("/getCompanyCoupons")
//    private ResponseEntity<?> getAllCoupons (){
//        return new ResponseEntity<>(companyService.getAllCompanyCoupons(companyId),HttpStatus.OK);
//    }
//
//    @GetMapping("/getCompanyCoupons/category")
//    private ResponseEntity<?> getCouponsByCategory (@PathVariable Category category){
//        return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(companyId, category),HttpStatus.OK);
//    }
//
//    @GetMapping("/getCompanyCoupons/maxPrice")
//    private ResponseEntity<?> getCouponsByMaxPrice (@PathVariable double maxPrice){
//        return new ResponseEntity<>(companyService.getCompanyCouponsByMaxPrice(companyId, maxPrice),HttpStatus.OK);
//    }
//
//    @GetMapping("/getCompanyDetails")
//    private ResponseEntity<?> getCompanyDetails (){
//        return new ResponseEntity<>(companyService.getCompanyDetails(companyId),HttpStatus.OK);
//    }



}
