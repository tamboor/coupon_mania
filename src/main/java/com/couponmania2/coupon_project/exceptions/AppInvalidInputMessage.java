package com.couponmania2.coupon_project.exceptions;

public enum AppInvalidInputMessage {
    COMPANY_NAME_CHANGE("Can't change company name."),
    NEGATIVE_PRICE("Can't insert a negative price"),
    NEGATIVE_AMOUNT("Can't insert a negative price"),
    UNMATCHING_COUPON ("Can't complete action - This coupon is owned by another company"),
    CATEGORY_NOT_EXIST ("Can't complete action - This category doesn't exist"),
    END_DATE_BEFORE_START_DATE ("Can't insert an end-date that is before the coupon's start date"),
    END_DATE_BEFORE_CURRENT_DATE ("Can't insert an end-date that is before the the current date");


    private final String message;

    AppInvalidInputMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
