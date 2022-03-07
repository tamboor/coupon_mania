package com.couponmania2.coupon_project.auth;

public enum ClientType {
    Admin ("admin"),
    Company("company"),
    Customer("customer");

    private String name;

    ClientType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
