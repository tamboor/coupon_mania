package com.couponmania2.coupon_project.exceptions;

import java.lang.reflect.Constructor;

public enum AppTargetExistsMessage {
    COMPANY_EXISTS("Can't complete action - Company already exists."),
    CUSTOMER_EXISTS("Can't complete action - Customer already exists.");

    private String message;

    AppTargetExistsMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
