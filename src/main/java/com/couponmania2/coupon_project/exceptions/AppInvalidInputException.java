package com.couponmania2.coupon_project.exceptions;

public class AppInvalidInputException extends Exception{
    public AppInvalidInputException(AppInvalidInputMessage err){
        super(err.getMessage());
    }
    public AppInvalidInputException(String message){
        super(message);
    }
}
