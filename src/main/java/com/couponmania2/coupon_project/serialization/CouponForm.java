package com.couponmania2.coupon_project.serialization;

import com.couponmania2.coupon_project.beans.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;

public class CouponForm {

    private long id;
    private Category category;
    private String title = "";
    private String description = "";
    private Date startDate;
    private Date endDate;
    private int amount = -1;
    private  double price = -1;
    private String image = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean checkNullFields() {
        return this.category == null || this.description == null || this.startDate == null ||
                this.endDate == null || this.title == null;
    }
}
