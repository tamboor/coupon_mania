package com.couponmania2.coupon_project.advice;

import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.DataTruncation;

//todo: add exceptions to advice
@ControllerAdvice
public class CouponSystemAdvice {
    /**
     * method that handles AppTargetNotFoundException.
     *
     * @param err the exception
     * @return response entity that holds the message of the exception and the response status
     */
    @ExceptionHandler(value = {AppTargetNotFoundException.class})
    ResponseEntity<ErrorDetails> notFoundException(Exception err) {
        return new ResponseEntity<>(new ErrorDetails("Target not found.", err.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * method that handles AppUnauthorizedRequestException.
     *
     * @param err the exception
     * @return response entity that holds the message of the exception and the response status
     */
    @ExceptionHandler(value = {AppUnauthorizedRequestException.class})
    ResponseEntity<ErrorDetails> unauthorizedException(Exception err) {
        return new ResponseEntity<>(new ErrorDetails("Unauthorized Request.", err.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * method that handles AppTargetExistsException.
     *
     * @param err the exception
     * @return response entity that holds the message of the exception and the response status
     */
    @ExceptionHandler(value = {AppTargetExistsException.class})
    ResponseEntity<ErrorDetails> targetExistsException(Exception err) {
        return new ResponseEntity<>(new ErrorDetails("Target already exists.", err.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * method that handles AppInvalidInputException.
     *
     * @param err the exception
     * @return response entity that holds the message of the exception and the response status
     */
    @ExceptionHandler(value = {AppInvalidInputException.class, IllegalArgumentException.class, PropertyValueException.class})
    ResponseEntity<ErrorDetails> invalidInputException(Exception err) {
        return new ResponseEntity<>(new ErrorDetails("Invalid input.", err.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * method that handles DataTruncation sql exception.
     *
     * @param err the exception
     * @return response entity that holds the message of the exception and the response status
     */
    @ExceptionHandler(value = {DataTruncation.class})
    ResponseEntity<ErrorDetails> invalidSqlInputException(Exception err) {
        return new ResponseEntity<>(new ErrorDetails("Invalid sql input.", "Data is invalid to the database."), HttpStatus.UNAUTHORIZED);
    }
}
