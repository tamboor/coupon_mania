package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CompanyRepo companyRepo;
    private final CustomerRepo customerRepo;
    private final PurchaseRepo purchaseRepo;
    private final String ADMIN_EMAIL = "admin@admin.com";
    private final String ADMIN_PASSWORD = "admin";

    /**
     * Checks if given credentials match admin login credentials.
     * @param email user email.
     * @param password user password.
     * @param clientType the type of the client.
     * @return returns 0 if credentials amtch admin credentials.
     * @throws AppUnauthorizedRequestException if credentials dont match admin credentials..
     */
    @Override
    public long checkCredentials(String email, String password, ClientType clientType) throws AppUnauthorizedRequestException, AppInvalidInputException {
        if (clientType==null){
            throw new AppInvalidInputException("this role doesn't exist!!!");
        }
        if (!(email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD) && clientType.equals(ClientType.admin))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return 0;
    }

    /**
     * adds a company to the database.
     * @param company the company to add.
     * @throws AppTargetExistsException if company name or email already exists in database.
     */
    @Override
    public void addCompany(Company company) throws AppTargetExistsException {
        if (companyRepo.existsByEmailOrName(company.getEmail(), company.getName())) {
            throw new AppTargetExistsException(AppTargetExistsMessage.COMPANY_EXISTS);
        }
        companyRepo.save(company);
    }

    /**
     * adds a customer to the database.
     * @param customer the company to add.
     * @throws AppTargetExistsException if customer email already exists in database.
     */
    @Override
    public void addCustomer(Customer customer) throws AppTargetExistsException {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new AppTargetExistsException(AppTargetExistsMessage.CUSTOMER_EXISTS);
        }
        customerRepo.save(customer);
    }

    /**
     * updates a company in the database.
     * @param company the company to update.
     * @throws AppTargetExistsException if company with given id and name doesnt exist in the database.
     */
    @Override
    public void updateCompany(Company company) throws AppInvalidInputException, AppTargetExistsException {
        if (!companyRepo.existsByIdAndName(company.getId(), company.getName())) {
            throw new AppInvalidInputException(AppInvalidInputMessage.COMPANY_NAME_CHANGE);
        }

        Optional<Company> companyOptional= companyRepo.findByEmail(company.getEmail());
        if (companyOptional.isPresent()){
            if (companyOptional.get().getId() != company.getId()){
                throw new AppTargetExistsException(AppTargetExistsMessage.EMAIL_EXISTS);
            }
        }

        companyRepo.save(company);
    }

    /**
     * updates a customer in the database.
     * @param customer the company to update.
     * @throws AppTargetExistsException if customer with given id doesnt exist in the database.
     */
    @Override
    public void updateCustomer(Customer customer) throws AppTargetNotFoundException, AppTargetExistsException {
        if (!customerRepo.existsById(customer.getId())) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        Optional<Customer> customerOptional= customerRepo.findByEmail(customer.getEmail());
        if (customerOptional.isPresent()){
            if (customerOptional.get().getId() != customer.getId()){
                throw new AppTargetExistsException(AppTargetExistsMessage.EMAIL_EXISTS);
            }
        }
        customerRepo.save(customer);
    }

    /**
     * deletes a company from the database.
     * @param companyID id of the company to delete.
     * @throws AppTargetNotFoundException if the company wasnt found in the database.
     */
    @Override
    public void deleteCompany(long companyID) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyID)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        companyRepo.deleteById(companyID);
    }

    /**
     * deletes a customer from the database.
     * @param customerID id of the company to delete.
     * @throws AppTargetNotFoundException if the customer wasnt found in the database.
     */
    @Override
    public void deleteCustomer(long customerID) throws AppTargetNotFoundException {
        if (!customerRepo.existsById(customerID)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        customerRepo.deleteById(customerID);
    }

    /**
     * gets all the companies registered in the database.
     * @return all the companies in the database.
     */
    @Override
    public Set<Company> getAllCompanies() {
        return new HashSet<>(companyRepo.findAll());
    }

    /**
     * gets all the customers registered in the database.
     * @return all the customers in the database.
     */
    @Override
    public Set<Customer> getAllCustomers() {
        return new HashSet<>(customerRepo.findAll());
    }

    /**
     * gets one company from the database.
     * @param companyID the id of the company to get.
     * @return the company found.
     * @throws AppTargetNotFoundException if company with given id wasnt found in teh database.
     */
    @Override
    public Company getOneCompany(long companyID) throws AppTargetNotFoundException {

        Optional<Company> companyOptional = companyRepo.findById(companyID);
        if (companyOptional.isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return companyOptional.get();
    }

    /**
     * retrieves all company's coupons by a company ID.
     * @param companyID company ID to check its coupons
     * @return set of the company's coupons
     * @throws AppTargetNotFoundException if the company doesn't exist.
     */
    @Override
    public Set<Coupon> getCompanyCoupons(long companyID) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyID)){
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return companyRepo.getById(companyID).getCoupons();
    }

    /**
     * gets one customer from the database.
     * @param customerID the id of the customer to get.
     * @return the customer found.
     * @throws AppTargetNotFoundException if customer with given id wasnt found in teh database.
     */
    @Override
    public Customer getOneCustomer(long customerID) throws AppTargetNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(customerID);
        if (customerOptional.isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        return customerOptional.get();
    }

    /**
     * retrieves all customer's coupons by a customer ID.
     * @param customerID customer ID to check its coupons
     * @return set of the customer's coupons
     * @throws AppTargetNotFoundException if the customer doesn't exist.
     */
    @Override
    public Set<Coupon> getCustomerCoupons(long customerID) throws AppTargetNotFoundException {
        if (!customerRepo.existsById(customerID)){
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
       return  purchaseRepo.getAllCouponsOfCustomer(customerRepo.getById(customerID));
    }

    @Override
    public Customer getCustomerByEmail(String email) throws AppTargetNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findByEmail(email);
        if (customerOptional.isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        return customerOptional.get();
    }

    @Override
    public Company getCompanyByEmail(String email) throws AppTargetNotFoundException {
       Optional<Company> companyOptional = companyRepo.findByEmail(email);
        if (companyOptional.isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return companyOptional.get();
    }
}
