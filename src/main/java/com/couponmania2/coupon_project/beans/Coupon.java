package com.couponmania2.coupon_project.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Company company;

    @Column(nullable = false)
    //@ManyToOne
    private Category category;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 256)
    private String description;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    //TODO: create an annotation that deals with insertion of a negative number
    private int amount;

    @Column(nullable = false)
    //TODO: create an annotation that deals with insertion of a negative number
    private double price;
    @Column
    private String image;


    @ManyToMany( mappedBy = "coupons")
    private Set<Customer> owners = new HashSet<>();



    public Coupon(Coupon coupon) {
//        this.company = coupon.getCompany();
        this.category = coupon.getCategory();
        this.title = coupon.getTitle();
        this.description = coupon.getDescription();
        this.startDate = coupon.getStartDate();
        this.endDate = coupon.getEndDate();
        this.amount = coupon.getAmount();
        this.price = coupon.getPrice();
    }
}
