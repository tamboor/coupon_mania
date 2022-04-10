package com.couponmania2.coupon_project.exceptions;

public enum AppUnauthorizedRequestMessage {
    BAD_CREDENTIALS ("Unauthorized request - please try login again"),
    LOGIN_EXPIRED("your login time limit has expired. please login again"),
    INVALID_TOKEN("Auth token is not valid."),
    NO_LOGIN("Unauthorized request - please login to an authorized account.");

    private final String message;
    /**
     * c'tor that gets a string from an enum value and injects it to the message field
     * @param message the string message
     */
    AppUnauthorizedRequestMessage(String message){
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
