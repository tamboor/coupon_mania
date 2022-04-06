package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import com.couponmania2.coupon_project.serialization.CompanyForm;
import com.couponmania2.coupon_project.serialization.CustomerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
//todo: check all jwt shit

public class AdminController extends ClientController {
    private final AdminServiceImpl adminService;
    private final JwtUtils jwtUtils;
    private final ResponseEntityGenerator responseEntityGenerator;

    /**
     * tries to login an admin user.
     *
     * @param userDetails the details of the user.
     * @return response entity that holds a token and a response status.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     */
    @Override
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails)
            throws AppUnauthorizedRequestException, AppInvalidInputException {
        if (!userDetails.getRole().equals(ClientType.admin.getName())) {
            throw new AppInvalidInputException("Bad role input.");
        }
        userDetails.setId(adminService.checkCredentials(
                userDetails.getUserName(),
                userDetails.getUserPass(),
                ClientType.valueOf(userDetails.getRole())));

        return responseEntityGenerator.getResponseEntity(userDetails);

    }

    @PostMapping("/addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody CompanyForm companyForm) throws AppUnauthorizedRequestException, AppTargetExistsException {
        UserDetails userDetails = validate(token);
        adminService.addCompany(new Company(companyForm));
        return responseEntityGenerator.getResponseEntity(userDetails, HttpStatus.CREATED);
    }

    @PutMapping("/updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody CompanyForm companyForm) throws AppTargetNotFoundException, AppUnauthorizedRequestException, AppInvalidInputException {
        UserDetails userDetails = validate(token);

        Company companyToUpdate = adminService.getOneCompany(companyForm.getId());
        companyToUpdate.setEmail(companyForm.getEmail());
        companyToUpdate.setPassword(companyForm.getPassword());
        companyToUpdate.setName(companyForm.getName());

        adminService.updateCompany(companyToUpdate);

        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        adminService.deleteCompany(companyId);

        return responseEntityGenerator.getResponseEntity(userDetails);
    }


    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);

        return responseEntityGenerator.getResponseEntity(userDetails, adminService.getAllComapnies());

    }

    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, adminService.getOneCompany(companyId));
    }

    @GetMapping("/getCompanyCoupons/{companyId}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, adminService.getCompanyCoupons(companyId));
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody CustomerForm customerForm) throws AppUnauthorizedRequestException, AppTargetExistsException {
        UserDetails userDetails = validate(token);
        adminService.addCustomer(new Customer(customerForm));
        return responseEntityGenerator.getResponseEntity(userDetails);

    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody CustomerForm customerForm) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);

        Customer customerToUpdate = adminService.getOneCustomer(customerForm.getId());
        customerToUpdate.setEmail(customerForm.getEmail());
        customerToUpdate.setFirstName(customerForm.getFirstName());
        customerToUpdate.setLastName(customerForm.getLastName());
        customerToUpdate.setPassword(customerForm.getPassword());
        adminService.updateCustomer(customerToUpdate);

        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        adminService.deleteCustomer(customerId);
        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomer(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, adminService.getAllCustomers());
    }

    @GetMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, adminService.getOneCustomer(customerId));
    }

    @GetMapping("/getCustomerCoupons/{customerId}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, adminService.getCustomerCoupons(customerId));
    }

    private UserDetails validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.admin.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return user;
    }


}
