package com.couponmania2.coupon_project.beans;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name= "customer")

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false,length = 40)
    private String firstName;
    @Column(nullable = false,length = 40)
    private String lastName;
    @Column(nullable = false,length = 40)
    private String email;
    @Column(nullable = false,length = 40)
    private String password;

    @ManyToMany
    @JoinTable (name = "customer_coupons",
            joinColumns =
            @JoinColumn(name = "customer_id" , referencedColumnName = "id"),

            inverseJoinColumns =
            @JoinColumn(name = "coupon_id" , referencedColumnName = "id"))
    @Singular

    private Set<Coupon> coupons = new HashSet<>();

//    public void addCoupon(Coupon coupon){
//        coupons.add(coupon);
//        coupon.getOwners().add(this);
//    }
//
//    public void removeCoupon(Coupon coupon){
//        coupons.remove(coupon);
//        coupon.getOwners().remove(this);
//    }




}
