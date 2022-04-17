package com.couponmania2.coupon_project.serialization;


/**
 * an object that hold the data that will be passed by the API.
 */
public class CustomerForm {

    private long id;
    private String firstName ;
    private String lastName ;
    private String email ;
    private String password ;

    /**
     * getter for id
     *
     * @return the id (long)
     */
    public long getId() {
        return id;
    }

    /**
     * setter for the id
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * getter for first name
     *
     * @return String first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter for first name
     *
     * @param firstName first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter for the last name
     *
     * @return String last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter for last name
     *
     * @param lastName last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return this.email == null || this.firstName == null || this.lastName == null || this.password == null;
    }
}
