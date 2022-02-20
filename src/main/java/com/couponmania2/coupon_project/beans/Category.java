package com.couponmania2.coupon_project.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table (name = "categories")
public enum Category {
    Food ("food"),
    Xtreme ("xterme"),
    Cars ("cars"),
    Vacation ("vacation"),
    Tattoos ("tattoos");

    //@Id
    private String name;

    private Category (String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
