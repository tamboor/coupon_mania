package com.couponmania2.coupon_project.exceptions;

public class AppUnauthorizedRequestException extends Exception{
    public AppUnauthorizedRequestException(AppUnauthorizedRequestException err){
        super(err.getMessage());
    }
    public AppUnauthorizedRequestException(String message){
        super(message);
    }
}
