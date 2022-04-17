package com.couponmania2.coupon_project.beans;

import com.couponmania2.coupon_project.serialization.CouponForm;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @ManyToOne
    @JsonIgnore
    private Company company;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 256)
    private String description;

    @Column(nullable = false)
    private java.sql.Date startDate;

    @Column(nullable = false)
    private java.sql.Date endDate;

    @Column(nullable = false)
    private int amount;


    @Column(nullable = false)
    private double price;
    @Column
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "coupon", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Purchase> purchases = new HashSet<>();


    protected Coupon() {
    }

    /**
     * C'tor for coupon form
     *
     * @param form    Coupon form
     * @param company Company
     */
    public Coupon(CouponForm form, Company company) {
        this(company
                , form.getCategory(),
                form.getTitle(),
                form.getDescription(),
                form.getStartDate(),
                form.getEndDate(),
                form.getAmount(),
                form.getPrice(),
                form.getImage());
    }

    /**
     * C'tor that gets coupon and passes it's properties to the co-responding c'tor.
     *
     * @param coupon Coupons
     */
    public Coupon(Coupon coupon) {
        this(coupon.getCompany(), coupon.getCategory(), coupon.getTitle(), coupon.getDescription(),
                coupon.getStartDate(), coupon.getEndDate()
                , coupon.getAmount(), coupon.getPrice(), coupon.getImage(), new HashSet<>());
    }

    /**
     * C'tor for a Coupon without purchase
     *
     * @param company     Coupons company
     * @param category    Coupons category
     * @param title       Coupons title
     * @param description Coupons description
     * @param startDate   Coupons start date
     * @param endDate     Coupons end date
     * @param amount      Coupons amount
     * @param price       Coupons price
     * @param image       Coupons image
     */
    public Coupon(Company company, Category category, String title, String description, java.sql.Date startDate, Date endDate, int amount, double price, String image) {
        this(company, category, title, description, startDate, endDate, amount, price, image, new HashSet<>());
    }

    /**
     * C'tor for a Coupon with purchase
     *
     * @param company     Coupons company
     * @param category    Coupons category
     * @param title       Coupons title
     * @param description Coupons description
     * @param startDate   Coupons start date
     * @param endDate     Coupons end date
     * @param amount      Coupons amount
     * @param price       Coupons price
     * @param image       Coupons image
     * @param purchases   Coupon purchases
     */
    public Coupon(Company company, Category category, String title, String description, java.sql.Date startDate, java.sql.Date endDate, int amount, double price, String image, Set<Purchase> purchases) {
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        setAmount(amount);
        setPrice(price);
        this.image = image;
        this.purchases = purchases;
    }

//    public Coupon(Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
//        this(null, category, title, description, startDate, endDate, amount, price, image, new HashSet<>());
//    }

    /**
     * Getter for coupon id
     *
     * @return Coupon id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for coupon id
     *
     * @param id Coupon id
     */
    private void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for company that owns the coupon
     *
     * @return Company owner
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Setter for company that owns the coupon
     *
     * @param company Company owner
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Getter for coupon category
     *
     * @return coupon category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Setter for coupon category
     *
     * @param category coupon category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Getter for coupon title
     *
     * @return coupon title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for coupon title
     *
     * @param title coupon title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for coupon description
     *
     * @return coupon description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for coupon description
     *
     * @param description coupon description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for coupon start date
     *
     * @return coupon start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter for coupon startDate
     *
     * @param startDate coupon start Date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter for coupon end date
     *
     * @return coupon End date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter for coupon end Date
     *
     * @param endDate coupon end Date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter for coupon amount
     *
     * @return coupon amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Setter for coupon amount
     *
     * @param amount coupon amount
     */
    public void setAmount(int amount) {
        this.amount= amount<0? 0 : amount;
    }

    /**
     * Getter for coupon price
     *
     * @return coupon price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for coupon price
     *
     * @param price coupon price
     */
    public void setPrice(double price) {
        this.price = price<0? 0 : price;
    }

    /**
     * Getter for coupon image
     *
     * @return coupon image
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter for coupon image
     *
     * @param image coupon image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Getter for coupon purchases
     *
     * @return coupon purchases
     */
    public Set<Purchase> getPurchases() {
        return purchases;
    }

    /**
     * Setter for coupon purchases
     *
     * @param purchases coupon purchases
     */
    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }


}
