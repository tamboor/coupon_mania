package com.couponmania2.coupon_project.serialization;

import com.couponmania2.coupon_project.beans.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;

public class CouponForm {

    private long id;
    private Category category;
    private String title  ;
    private String description ;
    private Date startDate;
    private Date endDate;
    private int amount = -1;
    private double price = -1;
    private String image ;

    /**
     * getter for id
     *
     * @return the id (long)
     */
    public long getId() {
        return id;
    }

    /**
     * setter for the id
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * getter for the coupon's category
     *
     * @return category of the coupon
     */
    public Category getCategory() {
        return category;
    }

    /**
     * setter for the coupon's category
     *
     * @param category category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * getter for the coupon's title
     *
     * @return title of the coupon
     */
    public String getTitle() {
        return title;
    }
    /**
     * setter for the coupon's title
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for the coupon's description
     *
     * @return description of the coupon
     */
    public String getDescription() {
        return description;
    }
    /**
     * setter for the coupon's description
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for the coupon's start date
     *
     * @return start date of the coupon
     */
    public Date getStartDate() {
        return startDate;
    }
    /**
     * setter for the coupon's start date
     *
     * @param startDate the start date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * getter for the coupon's end date
     *
     * @return end date of the coupon
     */
    public Date getEndDate() {
        return endDate;
    }
    /**
     * setter for the coupon's end date
     *
     * @param endDate the end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * getter for the coupon's amount
     *
     * @return amount of the coupon
     */
    public int getAmount() {
        return amount;
    }
    /**
     * setter for the coupon's amount
     *
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * getter for the coupon's price
     *
     * @return price of the coupon
     */
    public double getPrice() {
        return price;
    }
    /**
     * setter for the coupon's price
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter for the coupon's image
     *
     * @return image of the coupon
     */
    public String getImage() {
        return image;
    }
    /**
     * setter for the coupon's image
     *
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }
    /**
     * boolean method that checks if any of the Object fields is null.
     * @return true if there are null fields, false if not.
     */
    public boolean checkNullFields() {
        return this.category == null || this.description == null || this.startDate == null ||
                this.endDate == null || this.title == null;
    }
}
