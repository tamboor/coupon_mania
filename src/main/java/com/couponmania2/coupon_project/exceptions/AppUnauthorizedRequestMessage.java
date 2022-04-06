package com.couponmania2.coupon_project.exceptions;

public enum AppUnauthorizedRequestMessage {
    BAD_CREDENTIALS ("Unauthorized request - please try login again"),
    LOGIN_EXPIRED("your login time limit has expired. please login again"),
    INVALID_TOKEN("Auth token is not valid."),
    NO_LOGIN("Unauthorized request - please login to an authorized account.");

    private final String message;

    AppUnauthorizedRequestMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
