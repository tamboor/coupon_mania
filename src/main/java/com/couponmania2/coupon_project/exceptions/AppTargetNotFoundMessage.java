package com.couponmania2.coupon_project.exceptions;

public enum AppTargetNotFoundMessage {
    COMPANY_NOT_FOUND("Can't do action - company was not found."),
    CUSTOMER_NOT_FOUND("Can't do action - customer was not found."),
    COUPON_NOT_FOUND ("Can't complete action - Coupon was not found.");

     private String message;

    AppTargetNotFoundMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
