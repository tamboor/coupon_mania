package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityGenerator {

    public ResponseEntity<?> getResponseEntity(JwtUtils utils, UserDetails userDetails) {
        return getResponseEntity(utils , userDetails , HttpStatus.OK , null);
    }

    public ResponseEntity<?> getResponseEntity(JwtUtils utils, UserDetails userDetails, HttpStatus httpStatus) {
        return getResponseEntity(utils , userDetails , httpStatus , null);
    }

    //TODO: check change to generic
    public ResponseEntity<?> getResponseEntity(JwtUtils utils, UserDetails userDetails, HttpStatus httpStatus,Object body){
        return ResponseEntity.status(httpStatus).headers(utils.getHeaderWithToken(userDetails)).body(body);
    }

    public ResponseEntity<?> getResponseEntity(JwtUtils utils, UserDetails userDetails ,Object body){
        return getResponseEntity(utils , userDetails , HttpStatus.OK , body);
    }
}
