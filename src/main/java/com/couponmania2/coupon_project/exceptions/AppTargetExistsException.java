package com.couponmania2.coupon_project.exceptions;

public class AppTargetExistsException extends Exception{
    public AppTargetExistsException(AppTargetExistsMessage err){
        super(err.getMessage());
    }
    public AppTargetExistsException(String message){
        super(message);
    }
}
