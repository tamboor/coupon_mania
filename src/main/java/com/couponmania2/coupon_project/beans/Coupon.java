package com.couponmania2.coupon_project.beans;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@Entity
@Table (name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private long companyId;
    @Column(nullable = false)
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
    private String image;
}
