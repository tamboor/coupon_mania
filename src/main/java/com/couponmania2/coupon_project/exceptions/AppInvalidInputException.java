package com.couponmania2.coupon_project.exceptions;

public class AppInvalidInputException extends Exception{
    /**
     * c'tor that gets an enum and supers it's message to the corresponding c'tor of Exception class.
     * @param err the error enum to implement
     */
    public AppInvalidInputException(AppInvalidInputMessage err){
        super(err.getMessage());
    }

    /**
     * c'tor that gets string and supers it to the corresponding c'tor of Exception class.
     * @param message the message to implement
     */
    public AppInvalidInputException(String message){
        super(message);
    }
}
