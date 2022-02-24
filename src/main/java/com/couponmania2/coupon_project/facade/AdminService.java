package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;

import java.util.Set;

public interface    AdminService {
    void addCompany (Company company) throws Exception;
    void addCustomer(Customer customer) throws Exception;
    void updateCompany(Company company) throws AppTargetNotFoundException;
    void updateCustomer(Customer customer) throws AppTargetNotFoundException;
    void deleteCompany(int companyID) throws AppTargetNotFoundException;
    void deleteCustomer(int customerID) throws AppTargetNotFoundException;
    Set<Company> getAllComapnies();
    Set<Customer> getAllCustomers();
    Company getOneCompany(int companyID) throws AppTargetNotFoundException;
    Customer getOneCustomer(int customerID) throws AppTargetNotFoundException;
}
