package com.couponmania2.coupon_project.beans;

/**
 * Possible categoris for coupons.
 */
public enum Category {
    food("food"),
    xtreme("xterme"),
    cars("cars"),
    vacation("vacation"),
    tattoos("tattoos");


    private final String name;

    /**
     * C'tor to set the name value based on the value stored in the enums.
     *
     * @param name
     */
    Category(String name) {
        this.name = name;
    }

    /**
     * Gets the name value of the enum.
     *
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

}
