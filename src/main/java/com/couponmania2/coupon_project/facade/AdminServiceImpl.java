package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
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
    private final String ADMIN_EMAIL = "admin@admin.com";
    private final String AMDIN_PASSWORD = "admin";


    @Override
    public long checkCredentials(String email, String password, ClientType clientType) throws AppUnauthorizedRequestException {
        if (!(email.equals(ADMIN_EMAIL) && password.equals(AMDIN_PASSWORD) && clientType.equals(ClientType.ADMIN))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return 0;
    }

    @Override
    public void addCompany(Company company) throws AppTargetExistsException {
        if (companyRepo.existsByEmailOrName(company.getEmail(), company.getName())) {
            throw new AppTargetExistsException(AppTargetExistsMessage.COMPANY_EXISTS);
        }
        companyRepo.save(company);
    }

    @Override
    public void addCustomer(Customer customer) throws AppTargetExistsException {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new AppTargetExistsException(AppTargetExistsMessage.CUSTOMER_EXISTS);
        }
        customerRepo.save(customer);
    }

    @Override
    public void updateCompany(Company company) throws AppTargetNotFoundException {
//        companyRepo.existsByIdAndName(company)
        if (!companyRepo.existsByIdAndName(company.getId(), company.getName())) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        companyRepo.save(company);
    }

    @Override
    public void updateCustomer(Customer customer) throws AppTargetNotFoundException {
        if (!customerRepo.existsById(customer.getId())) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        customerRepo.save(customer);
    }

    @Override
    public void deleteCompany(long companyID) throws AppTargetNotFoundException {
        if (!companyRepo.existsById(companyID)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        companyRepo.deleteById(companyID);
    }

    @Override
    public void deleteCustomer(long customerID) throws AppTargetNotFoundException {
        if (!customerRepo.existsById(customerID)) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        customerRepo.deleteById(customerID);
    }

    @Override
    public Set<Company> getAllComapnies() {
        return new HashSet<>(companyRepo.findAll());
    }

    @Override
    public Set<Customer> getAllCustomers() {
        return new HashSet<>(customerRepo.findAll());
    }

    @Override
    public Company getOneCompany(long companyID) throws AppTargetNotFoundException {
        Optional<Company> companyOptional = companyRepo.findById(companyID);
        if (companyOptional.isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
        }
        return companyOptional.get();
    }

    @Override
    public Optional<Customer> getOneCustomer(long customerID) throws AppTargetNotFoundException {
        if (customerRepo.findById(customerID).isEmpty()) {
            throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
        }
        return customerRepo.findById(customerID);
    }
}
