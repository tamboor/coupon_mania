package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guest")
@RequiredArgsConstructor
public class GuestController {
    private final CustomerServiceImpl customerService;

    /**
     * gets all coupons from the database.
     *
     * @return ResponseEntity containing HttpStatus, a new token and all the coupons in the database.
     */
    @GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(customerService.getAllCoupons(), HttpStatus.OK);
    }
}
