package com.couponmania2.coupon_project.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "coupon_purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    private Customer customer;

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
