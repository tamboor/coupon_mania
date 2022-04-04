package com.couponmania2.coupon_project.advice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorDetails {
    private final String error;
    private final String description;

    /**
     * getter for the error title
     * @return the error title
     */
    public String getError() {
        return error;
    }

    /**
     * getter for the error description
     * @return the error description
     */
    public String getDescription() {
        return description;
    }
}
