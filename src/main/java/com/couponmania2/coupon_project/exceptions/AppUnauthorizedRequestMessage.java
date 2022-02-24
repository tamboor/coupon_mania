package com.couponmania2.coupon_project.exceptions;

public enum AppUnauthorizedRequestMessage {
    NO_LOGIN

    private String message;

    AppUnauthorizedRequestMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
