package com.couponmania2.coupon_project.advice;

import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CouponSystemAdvice {
    @ExceptionHandler(value = {AppTargetNotFoundException.class})
    ResponseEntity<ErrorDetails>  notFoundException(Exception err){
        return new ResponseEntity<>(new ErrorDetails("Target not found." , err.getMessage()), HttpStatus.NOT_FOUND);
    }
}
