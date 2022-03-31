package com.couponmania2.coupon_project.auth;

public enum ClientType {
    admin("admin"),
    company("company"),
    customer("customer");

    private final String name;

    ClientType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
