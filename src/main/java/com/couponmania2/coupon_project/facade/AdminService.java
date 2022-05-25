package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;

import java.util.Set;

public interface    AdminService {
    /**
     * Checks if given credentials match admin login credentials.
     * @param email user email.
     * @param password user password.
     * @param clientType the type of the client.
     * @return returns 0 if credentials amtch admin credentials.
     * @throws AppUnauthorizedRequestException if credentials dont match admin credentials..
     */
    long checkCredentials(String email, String password, ClientType clientType) throws AppUnauthorizedRequestException, AppInvalidInputException;

    /**
     * adds a company to the database.
     * @param company the company to add.
     * @throws AppTargetExistsException if company name or email already exists in database.
     */
    void addCompany (Company company) throws Exception;

    /**
     * adds a customer to the database.
     * @param customer the company to add.
     * @throws AppTargetExistsException if customer email already exists in database.
     */
    void addCustomer(Customer customer) throws Exception;

    /**
     * updates a company in the database.
     * @param company the company to update.
     * @throws AppTargetExistsException if company with given id and name doesnt exist in the database.
     */
    void updateCompany(Company company) throws AppTargetNotFoundException, AppInvalidInputException, AppTargetExistsException;

    /**
     * updates a customer in the database.
     * @param customer the company to update.
     * @throws AppTargetExistsException if customer with given id doesnt exist in the database.
     */
    void updateCustomer(Customer customer) throws AppTargetNotFoundException, AppTargetExistsException;

    /**
     * deletes a company from the database.
     * @param companyID id of the company to delete.
     * @throws AppTargetNotFoundException if the company wasnt found in the database.
     */
    void deleteCompany(long companyID) throws AppTargetNotFoundException;

    /**
     * deletes a customer from the database.
     * @param customerID id of the company to delete.
     * @throws AppTargetNotFoundException if the customer wasnt found in the database.
     */
    void deleteCustomer(long customerID) throws AppTargetNotFoundException;

    /**
     * gets all the companies registered in the database.
     * @return all the companies in the database.
     */
    Set<Company> getAllCompanies();

    /**
     * gets all the customers registered in the database.
     * @return all the customers in the database.
     */
    Set<Customer> getAllCustomers();

    /**
     * gets one company from the database.
     * @param companyID the id of the company to get.
     * @return the company found.
     * @throws AppTargetNotFoundException if company with given id wasnt found in teh database.
     */
    Company getOneCompany(long companyID) throws AppTargetNotFoundException;

    /**
     * retrieves all company's coupons by a company ID.
     * @param companyID company ID to check its coupons
     * @return set of the company's coupons
     * @throws AppTargetNotFoundException if the company doesn't exist.
     */
    Set<Coupon> getCompanyCoupons (long companyID) throws AppTargetNotFoundException;

    /**
     * gets one customer from the database.
     * @param customerID the id of the customer to get.
     * @return the customer found.
     * @throws AppTargetNotFoundException if customer with given id wasnt found in teh database.
     */
    Customer getOneCustomer(long customerID) throws AppTargetNotFoundException;

    /**
     * retrieves all customer's coupons by a customer ID.
     * @param customerID customer ID to check its coupons
     * @return set of the customer's coupons
     * @throws AppTargetNotFoundException if the customer doesn't exist.
     */
    Set<Coupon> getCustomerCoupons (long customerID) throws AppTargetNotFoundException;

    Customer getCustomerByEmail(String email) throws AppTargetNotFoundException;
    Company getCompanyByEmail(String email) throws AppTargetNotFoundException;
}
