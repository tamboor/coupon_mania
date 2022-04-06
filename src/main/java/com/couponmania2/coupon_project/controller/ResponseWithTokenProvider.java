package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseWithTokenProvider {
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> getResponseEntity(UserDetails userDetails) {
        return getResponseEntity(userDetails , HttpStatus.OK , null);
    }

    public ResponseEntity<?> getResponseEntity(UserDetails userDetails, HttpStatus httpStatus) {
        return getResponseEntity(userDetails , httpStatus , null);
    }

    //TODO: check change to generic
    public ResponseEntity<?> getResponseEntity(UserDetails userDetails, HttpStatus httpStatus,Object body){
        return ResponseEntity.status(httpStatus).headers(jwtUtils.getHeaderWithToken(userDetails)).body(body);
    }

    public ResponseEntity<?> getResponseEntity(UserDetails userDetails ,Object body){
        return getResponseEntity(userDetails , HttpStatus.OK , body);
    }
}
