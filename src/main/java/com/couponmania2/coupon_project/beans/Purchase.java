package com.couponmania2.coupon_project.beans;

import javax.persistence.*;

@Entity
@Table(name = "coupon_purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Coupon coupon;

    /**
     * Disable instantiating purchases.
     */
    private Purchase() {
    }

    /**
     * C'tor with customer and coupon.
     *
     * @param customer the customer.
     * @param coupon   the coupon.
     */
    public Purchase(Customer customer, Coupon coupon) {
        this.customer = customer;
        this.coupon = coupon;
    }

    /**
     * getter for the customer.
     *
     * @return the customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * setter for customer.
     *
     * @param customer customer.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * getter for the coupon.
     *
     * @return the coupon.
     */
    public Coupon getCoupon() {
        return coupon;
    }

    /**
     * setter for coupon.
     *
     * @param coupon the coupon.
     */
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

}
