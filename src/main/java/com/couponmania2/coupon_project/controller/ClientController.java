package com.couponmania2.coupon_project.controller;


import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class ClientController {
    public abstract ResponseEntity<?> login( UserDetails userDetails)
            throws AppUnauthorizedRequestException;
}
