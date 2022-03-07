package com.couponmania2.coupon_project.exceptions;

public enum AppUnauthorizedRequestMessage {
    BAD_CREDENTIALS ("Unauthorized request - please try login again"),
    LOGIN_EXPIRED("your login time limit has expired. please login again"),
    NO_LOGIN("Unauthorized request - please login to an authorized account.");

    private String message;

    AppUnauthorizedRequestMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
