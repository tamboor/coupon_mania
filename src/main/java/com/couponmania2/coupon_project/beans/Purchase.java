package com.couponmania2.coupon_project.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "coupon_purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;
//
    @JsonBackReference
    @ManyToOne
    private Customer customer;

    @JsonBackReference
    @ManyToOne
    private Coupon coupon;

    protected Purchase(){}

    public Purchase(Customer customer , Coupon coupon){
        this.customer = customer;
        this.coupon = coupon;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

}
