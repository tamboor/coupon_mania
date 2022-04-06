package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;

import java.util.Optional;
import java.util.Set;

public interface    AdminService {
    long checkCredentials(String email, String password, ClientType clientType) throws AppUnauthorizedRequestException;
    void addCompany (Company company) throws Exception;
    void addCustomer(Customer customer) throws Exception;
    void updateCompany(Company company) throws AppTargetNotFoundException, AppInvalidInputException, AppTargetExistsException;
    void updateCustomer(Customer customer) throws AppTargetNotFoundException, AppTargetExistsException;
    void deleteCompany(long companyID) throws AppTargetNotFoundException;
    void deleteCustomer(long customerID) throws AppTargetNotFoundException;
    Set<Company> getAllComapnies();
    Set<Customer> getAllCustomers();
    Company getOneCompany(long companyID) throws AppTargetNotFoundException;
    Set<Coupon> getCompanyCoupons (long companyID) throws AppTargetNotFoundException;
    Customer getOneCustomer(long customerID) throws AppTargetNotFoundException;
    Set<Coupon> getCustomerCoupons (long customerID) throws AppTargetNotFoundException;
}
