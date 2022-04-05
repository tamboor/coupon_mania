package com.couponmania2.coupon_project.beans;

import com.couponmania2.coupon_project.serialization.CouponSerializer;
import com.couponmania2.coupon_project.serialization.CustomerForm;
import com.couponmania2.coupon_project.serialization.CustomerSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
//@JsonSerialize(using = CustomerSerializer.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    @Column(nullable = false,length = 40)
    private String firstName;
    @Column(nullable = false,length = 40)
    private String lastName;
    @Column(nullable = false,length = 40)
    private String email;
    @Column(nullable = false,length = 40)
    private String password;

    //@JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "customer",orphanRemoval = true , cascade = CascadeType.PERSIST , fetch = FetchType.LAZY)
    Set<Purchase> purchases = new HashSet<>();

    public Customer(CustomerForm customerForm){
        this(customerForm.getFirstName(),
                customerForm.getLastName(),
                customerForm.getEmail(),
                customerForm.getLastName(),
                new HashSet<>());
    }

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

    public long getId() {
        return id;
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
//@JsonManagedReference (value = "customer-purchase")
    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", purchases=" + purchases +
                '}';
    }
}
