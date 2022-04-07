package com.couponmania2.coupon_project.auth;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ClientType {
    admin("admin"),
    company("company"),
    customer("customer");

    private final String name;



    /**
     * c'tor that applies a given string name to enum's value name
     *
     * @param name name
     */
    ClientType(String name) {
        this.name = name;
    }

    /**
     * getter for the name
     *
     * @return enum's value name
     */
    public String getName() {
        return this.name;
    }

}
