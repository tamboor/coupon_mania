package com.couponmania2.coupon_project.beans;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    food("food"),
    xtreme("xterme"),
    cars("cars"),
    vacation("vacation"),
    tattoos("tattoos");

    private final String name;

    Category(String name){
        this.name = name;
    }
@JsonValue
    public String getName(){
        return this.name;
    }

}
