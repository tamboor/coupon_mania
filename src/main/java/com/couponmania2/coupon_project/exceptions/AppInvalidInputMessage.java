package com.couponmania2.coupon_project.exceptions;

public enum AppInvalidInputMessage {
    COMPANY_NAME_CHANGE("Can't change company name."),
    NEGATIVE_PRICE("Can't insert a negative price"),
    NEGATIVE_AMOUNT("Can't insert a negative price"),
    UNMATCHING_COUPON("Can't complete action - This coupon is owned by another company"),
    CATEGORY_NOT_EXIST("Can't complete action - This category doesn't exist"),
    END_DATE_BEFORE_START_DATE("Can't insert an end-date that is before the coupon's start date"),
    END_DATE_BEFORE_CURRENT_DATE("Can't insert an end-date that is before the the current date"),
    NULL_FIELDS("Can't complete action - one of the fields is null"),
    ROLE_NOT_EXIST("Can't complete action - role doesn't exist");


    private final String message;

    /**
     * c'tor that gets a string from an enum value and injects it to the message field
     * @param message the string message
     */
    AppInvalidInputMessage(String message) {
        this.message = message;
    }

    /**
     * getter to the enum's value message
     * @return the value's message
     */
    public String getMessage() {
        return message;
    }

}
