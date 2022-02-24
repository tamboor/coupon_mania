package com.couponmania2.coupon_project.exceptions;

public class AppTargetNotFoundException extends Exception{
    public AppTargetNotFoundException(AppTargetNotFoundMessage err){
        super(err.getMessage());
    }
    public AppTargetNotFoundException(String message){
        super(message);
    }
}
