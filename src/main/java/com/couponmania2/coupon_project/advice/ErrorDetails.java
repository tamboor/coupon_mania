package com.couponmania2.coupon_project.advice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorDetails {
    private final String error;
    private final String description;

    public String getError() {
        return error;
    }
    public String getDescription() {
        return description;
    }
}
