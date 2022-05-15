package com.couponmania2.coupon_project.beans;

import com.couponmania2.coupon_project.serialization.CustomerForm;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    @Column(nullable = false, length = 40)
    private String firstName;
    @Column(nullable = false, length = 40)
    private String lastName;
    @Column(nullable = false, length = 40)
    private String email;
    @Column(nullable = false, length = 40)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    Set<Purchase> purchases = new HashSet<>();

    /**
     * C'tor to create a customer from a Customer Form (Which is the data bean used in the Rest API.)
     *
     * @param customerForm The form to get the data from.
     */
    public Customer(CustomerForm customerForm) {
        this(customerForm.getFirstName(),
                customerForm.getLastName(),
                customerForm.getEmail(),
                customerForm.getPassword(),
                new HashSet<>());
    }

    /**
     * Private c'tor to disable instantiating a customer with no data.
     */
    protected Customer() {
    }

    /**
     * Customer c'tor with purchases.
     *
     * @param firstName first name of the customer.
     * @param lastName  last name of the customer.
     * @param email     email of the customer.
     * @param password  password of the customer.
     * @param purchases all purchases of the customer.
     */
    public Customer(String firstName, String lastName, String email, String password, Set<Purchase> purchases) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.purchases = purchases;
    }

    /**
     * Customer c'tor.
     *
     * @param firstName first name of the customer.
     * @param lastName  last name of the customer.
     * @param email     email of the customer.
     * @param password  password of the customer.
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this(firstName, lastName, email, password, new HashSet<>());
    }

    /**
     * getter for id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * getter for first name.
     *
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter for first name.
     *
     * @param firstName the value to set first name to.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter for last name.
     *
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter for last name.
     *
     * @param lastName the value to set first name to.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getter for email.
     *
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for email.
     *
     * @param email the value to set first name to.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter for password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password.
     *
     * @param password the value to set first name to.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for purchases.
     *
     * @return the purchases.
     */
    public Set<Purchase> getPurchases() {
        return purchases;
    }

    /**
     * setter for purchases.
     *
     * @param purchases the value to set first name to.
     */
    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }


    /**
     * Customer toString method.
     *
     * @return the custmer string.
     */
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
