package com.couponmania2.coupon_project.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CompanyForm {

    private Long id;
    private String name;
    private String email;
    private String password;
    /**
     * getter for id
     *
     * @return the id (long)
     */
    public Long getId() {
        return id;
    }

    /**
     * setter for the id
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter for the company name
     *
     * @return String company name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for company name
     *
     * @param name company name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for the email
     *
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for the email
     *
     * @param email email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter for the password
     *
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for the password
     *
     * @param password pasword to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * boolean method that checks if any of the Object fields is null.
     * @return true if there are null fields, false if not.
     */
    public boolean checkNullFields() {
        return this.email == null || this.name == null || this.password == null;
    }
}
