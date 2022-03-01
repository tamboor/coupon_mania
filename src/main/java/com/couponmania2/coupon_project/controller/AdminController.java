package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
//ggg
@RestController
@RequestMapping("admin")
//@RequiredArgsConstructor
public class AdminController extends ClientController {
    private final AdminServiceImpl adminService;

    AdminController(AdminServiceImpl adminService){
        this.adminService = adminService;
        getAllCustomer();

    }


    @Override
    public boolean login(String email, String password) {
        return false;
    }

    @PostMapping("/addCompany")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestBody Company company) throws Exception {
        adminService.addCompany(company);
    }

    @PutMapping("/updateCompany")
    @ResponseStatus(HttpStatus.OK)
    public void updateCompany(@RequestBody Company company) throws AppTargetNotFoundException {
        adminService.updateCompany(company);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable int companyId) throws AppTargetNotFoundException {
        adminService.deleteCompany(companyId);
    }

    //todo: nir needs to read about response entity
    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies() {
        return new ResponseEntity<>(adminService.getAllComapnies(), HttpStatus.OK);
    }

    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@PathVariable int companyId) throws AppTargetNotFoundException {
        return new ResponseEntity<>(adminService.getOneCompany(companyId), HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestBody Customer customer) throws Exception {
        //todo: change exception
        adminService.addCustomer(customer);
    }

    @PutMapping("/updateCustomer")
    public void updateCustomer(Customer customer) throws AppTargetNotFoundException {
        adminService.updateCustomer(customer);
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(int customerId) throws AppTargetNotFoundException {
        adminService.deleteCustomer(customerId);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomer() {
        return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@PathVariable int customerId) throws AppTargetNotFoundException {
        return new ResponseEntity<>(adminService.getOneCustomer(customerId), HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneCustomer(customerId));
//        return new ResponseEntity<Customer>(HttpStatus.);

    }

}
