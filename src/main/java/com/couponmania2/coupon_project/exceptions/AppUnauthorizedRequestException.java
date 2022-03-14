package com.couponmania2.coupon_project.exceptions;

public class AppUnauthorizedRequestException extends Exception{
    public AppUnauthorizedRequestException(AppUnauthorizedRequestMessage err){
        super(err.getMessage());
    }
    public AppUnauthorizedRequestException(String message){
        super(message);
    }
}
