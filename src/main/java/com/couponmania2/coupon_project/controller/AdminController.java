package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController extends ClientController {
    private final AdminServiceImpl adminService;
    private final JwtUtils jwtUtils;

    @Override
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws AppUnauthorizedRequestException {
        userDetails.setId(adminService.checkCredentials(userDetails.getUserName(), userDetails.getUserPass()));
        return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);
    }

    @PostMapping("/addCompany")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws Exception {
       jwtUtils.validateToken(token);
       adminService.addCompany(company);
    }

    @PutMapping("/updateCompany")
    @ResponseStatus(HttpStatus.OK)
    public void updateCompany(@RequestBody Company company) throws AppTargetNotFoundException {
        adminService.updateCompany(company);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable long companyId) throws AppTargetNotFoundException {
        adminService.deleteCompany(companyId);
    }

    //todo: nir needs to read about response entity
    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        jwtUtils.validateToken(token);
        return new ResponseEntity<>(adminService.getAllComapnies(), HttpStatus.OK);
    }

    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@PathVariable long companyId) throws AppTargetNotFoundException {
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
    public void deleteCustomer(long customerId) throws AppTargetNotFoundException {
        adminService.deleteCustomer(customerId);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomer() {
        return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@PathVariable long customerId) throws AppTargetNotFoundException {
        return new ResponseEntity<>(adminService.getOneCustomer(customerId), HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneCustomer(customerId));
//        return new ResponseEntity<Customer>(HttpStatus.);

    }


}
