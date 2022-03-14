package com.couponmania2.coupon_project.exceptions;

public enum AppInvalidInputMessage {
    COMPANY_NAME_CHANGE("Can't change company name."),
    NEGATIVE_PRICE("Can't insert a negative price");

    private String message;

    AppInvalidInputMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
