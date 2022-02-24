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


//@NoArgsConstructor
@Entity
@Table(name= "customers")
public class Customer {
    public int getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @Column(nullable = false,length = 40)
    private String firstName;
    @Column(nullable = false,length = 40)
    private String lastName;
    @Column(nullable = false,length = 40)
    private String email;
    @Column(nullable = false,length = 40)
    private String password;

    @OneToMany(mappedBy = "customer",orphanRemoval = true , cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
    Set<Purchase> purchases = new HashSet<>();

    protected Customer(){}

    public Customer(String firstName, String lastName, String email, String password, Set<Purchase> purchases) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.purchases = purchases;
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this(firstName,lastName,email,password,new HashSet<>());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }
}
