package com.couponmania2.coupon_project.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false , length = 30)
    private String name;

    @Column(nullable = false , length = 40)
    private String email;

    @Column(nullable = false , length = 30)
    private String password;

//    @OneToMany(orphanRemoval = true , cascade = CascadeType.ALL , fetch = )
    @OneToMany(mappedBy = "company" , orphanRemoval = true , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Coupon> coupons;
}
