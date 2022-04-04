package com.couponmania2.coupon_project.controller;
//todo: complete

import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
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
    @GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons() throws AppUnauthorizedRequestException {
        return new ResponseEntity<>(customerService.getAllCoupons(), HttpStatus.OK);
    }
}
