package com.couponmania2.coupon_project.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

//@Data
@Entity
@Table(name = "coupons")

public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @JsonBackReference
    @ManyToOne
    private Company company;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 256)
    private String description;

    //TODO: check what date type needed
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

    @JsonManagedReference
    @OneToMany(mappedBy = "coupon", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Purchase> purchases = new HashSet<>();


    protected Coupon() {
    }

    public Coupon(Coupon coupon) {
        this(coupon.getCompany(), coupon.getCategory(), coupon.getTitle(), coupon.getDescription(),
                coupon.getStartDate(), coupon.getEndDate()
                , coupon.getAmount(), coupon.getPrice(), coupon.getImage(), new HashSet<>());
    }

    public Coupon(Company company, Category category, String title, String description, java.sql.Date startDate, Date endDate, int amount, double price, String image) {
        this(company, category, title, description, startDate, endDate, amount, price, image, new HashSet<>());
    }

    public Coupon(Company company, Category category, String title, String description, java.sql.Date startDate, java.sql.Date endDate, int amount, double price, String image, Set<Purchase> purchases) {
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
        this.purchases = purchases;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    private void setCompany(Company company) {
        this.company = company;
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

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company=" + company +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image +
                '}';
    }
}
