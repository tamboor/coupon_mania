package com.couponmania2.coupon_project.facade;

import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    CustomerRepo customerRepo;

    //todo: check if update checks can be handled in restcontroller

    @Override
    public void addCompany(Company company) throws Exception {
        if (companyRepo.existsByEmailOrName(company.getEmail() , company.getName())){
            throw new Exception();
        }
        companyRepo.save(company);
    }

    @Override
    public void addCustomer(Customer customer) throws Exception {
        if (customerRepo.existsByEmail(customer.getEmail())){
            throw new Exception();
        }
        customerRepo.save(customer);
    }

    @Override
    public void updateCompany(Company company) {
//        companyRepo.existsByIdAndName(company)
        if (!companyRepo.existsByIdAndName(company.getId() , company.getName())){
            //todo: throw exception
        }
        companyRepo.save(company);
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (!customerRepo.existsById(customer.getId())){
            //todo: throw exception
        }
        customerRepo.save(customer);
    }

    @Override
    public void deleteCompany(int companyID) {
        if (!companyRepo.existsById(companyID)){
            //todo: throw exception
        }
        companyRepo.deleteById(companyID);
    }

    @Override
    public void deleteCustomer(int customerID) {
        if (!customerRepo.existsById(customerID)){
            //todo: throw exception
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
    public Company getOneCompany(int companyID) {
       Optional<Company> companyOptional = companyRepo.findById(companyID);
       if (companyOptional.isEmpty()){
           //todo: throw exception
       }
       return companyOptional.get();
    }

    @Override
    public Customer getOneCustomer(int customerID) {
        Optional<Customer> customerOptional = customerRepo.findById(customerID);
        if (customerOptional.isEmpty()){
            //todo: throw exception
        }
        return customerOptional.get();
    }
}
