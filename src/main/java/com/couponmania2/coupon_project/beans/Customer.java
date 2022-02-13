package com.couponmania2.coupon_project.beans;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name= "customer")

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 40)
    private String firstName;
    @Column(nullable = false,length = 40)
    private String lastName;
    @Column(nullable = false,length = 40)
    private String email;
    @Column(nullable = false,length = 40)
    private String password;

    @ManyToMany (cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable (name = "customer_coupons" )
    @Singular
    private List<Coupon> coupons ;




}
