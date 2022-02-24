package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;

import java.util.Set;

public interface    AdminService {
    void addCompany (Company company) throws Exception;
    void addCustomer(Customer customer) throws Exception;
    void updateCompany(Company company);
    void updateCustomer(Customer customer);
    void deleteCompany(int companyID);
    void deleteCustomer(int customerID);
    Set<Company> getAllComapnies();
    Set<Customer> getAllCustomers();
    Company getOneCompany(int companyID);
    Customer getOneCustomer(int customerID);
}
