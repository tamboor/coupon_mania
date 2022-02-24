package com.couponmania2.coupon_project.advice;

import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
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

    @ExceptionHandler(value = {AppUnauthorizedRequestException.class})
    ResponseEntity<ErrorDetails>  unauthorizedException(Exception err){
        return new ResponseEntity<>(new ErrorDetails("Unauthorized Request." , err.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AppTargetExistsException.class})
    ResponseEntity<ErrorDetails>  targetExistsException(Exception err){
        return new ResponseEntity<>(new ErrorDetails("Target already exists." , err.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AppInvalidInputException.class})
    ResponseEntity<ErrorDetails>  invalidInputException(Exception err){
        return new ResponseEntity<>(new ErrorDetails("Invalid input." , err.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
