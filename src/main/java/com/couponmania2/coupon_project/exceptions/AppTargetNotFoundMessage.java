package com.couponmania2.coupon_project.exceptions;

public enum AppTargetNotFoundMessage {
    COMPANY_NOT_FOUND("Can't do action - company was not found."),
    CUSTOMER_NOT_FOUND("Can't do action - customer was not found."),
    COUPON_NOT_FOUND ("Can't complete action - Coupon was not found.");

     private final String message;
    /**
     * c'tor that gets a string from an enum value and injects it to the message field
     * @param message the string message
     */
    AppTargetNotFoundMessage(String message){
        this.message = message;
    }
    /**
     * getter to the enum's value message
     * @return the value's message
     */
    public String getMessage(){
        return message;
    }
}
