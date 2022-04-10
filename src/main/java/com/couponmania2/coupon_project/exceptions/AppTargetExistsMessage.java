package com.couponmania2.coupon_project.exceptions;

import java.lang.reflect.Constructor;

public enum AppTargetExistsMessage {
    COMPANY_EXISTS("Can't complete action - Company already exists."),
    CUSTOMER_EXISTS("Can't complete action - Customer already exists."),
    COUPON_EXISTS ("Can't complete action - Coupon already exists."),
    EMAIL_EXISTS("User of same type exists with this email.");

    private final String message;
    /**
     * c'tor that gets a string from an enum value and injects it to the message field
     * @param message the string message
     */
    AppTargetExistsMessage(String message){
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
