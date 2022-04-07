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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

// TODO: 07/04/2022 add notnull checks for all args in userDetails and forms. 
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController extends ClientController {
    private final AdminServiceImpl adminService;
    private final JwtUtils jwtUtils;
    private final ResponseWithTokenProvider responseEntityGenerator;

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
        if (userDetails.getRole()==null){
            throw new AppInvalidInputException("This role doesn't exist!!!");
        }
        System.out.println(userDetails.getRole().toLowerCase());
        System.out.println(ClientType.admin.getName());
//        if (!userDetails.getRole().toLowerCase().equals(ClientType.admin.getName())) {
//            throw new AppInvalidInputException("Bad role input.");
//        }
        userDetails.setId(adminService.checkCredentials(
                userDetails.getUserName(),
                userDetails.getUserPass(),
                ClientType.valueOf(userDetails.getRole().toLowerCase())));

        return responseEntityGenerator.getResponseEntity(userDetails);

    }

    /**
     * Adds a company to the database.
     *
     * @param token       authentication token.
     * @param companyForm Form representing the company to add.
     * @return response entity with httpstatus and new token.
     * @throws AppUnauthorizedRequestException if authentication failed.
     * @throws AppTargetExistsException        if company exists in the database.
     */
    @PostMapping("/addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody CompanyForm companyForm) throws AppUnauthorizedRequestException, AppTargetExistsException {
        UserDetails userDetails = validate(token);
        adminService.addCompany(new Company(companyForm));
        return responseEntityGenerator.getResponseEntity(userDetails, HttpStatus.CREATED);
    }

    /**
     * updates a company in the database.
     *
     * @param token       authentication token.
     * @param companyForm Form representing the company to update.
     * @return response entity with httpstatus and new token.
     * @throws AppTargetNotFoundException      if company to update wasnt found.
     * @throws AppUnauthorizedRequestException if authentication failed.
     * @throws AppInvalidInputException        if the input had fields that are not allowed tobe changed.
     * @throws AppTargetExistsException        if input tried to changed to an existing email.
     */
    @PutMapping("/updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody CompanyForm companyForm) throws AppTargetNotFoundException, AppUnauthorizedRequestException, AppInvalidInputException, AppTargetExistsException {
        UserDetails userDetails = validate(token);

        Company companyToUpdate = adminService.getOneCompany(companyForm.getId());
        companyToUpdate.setEmail(companyForm.getEmail());
        companyToUpdate.setPassword(companyForm.getPassword());
        companyToUpdate.setName(companyForm.getName());

        adminService.updateCompany(companyToUpdate);

        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    /**
     * Deletes a company from the database.
     *
     * @param token     authentication token.
     * @param companyId the id of the company to delete.
     * @return response entity with httpstatus and new token.
     * @throws AppTargetNotFoundException      if company to delete wasnt found.
     * @throws AppUnauthorizedRequestException if authentication failed.
     */
    @DeleteMapping("/deleteCompany/{companyId}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        adminService.deleteCompany(companyId);

        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    /**
     * @param token
     * @return
     * @throws AppUnauthorizedRequestException
     */
    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);

        return responseEntityGenerator.getResponseEntity(userDetails, adminService.getAllComapnies());
    }

    /**
     * Retrieves one company from the database.
     * @param token auth token
     * @param companyId company to retrieves.
     * @returnresponse entity containing the httpresponse, auth token and the company (serialized and in body.).
     * @throws AppTargetNotFoundException
     * @throws AppUnauthorizedRequestException
     */
    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, adminService.getOneCompany(companyId));
    }

    /**
     * 
     * @param token
     * @param companyId
     * @return
     * @throws AppTargetNotFoundException
     * @throws AppUnauthorizedRequestException
     */
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
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody CustomerForm customerForm) throws AppTargetNotFoundException, AppUnauthorizedRequestException, AppTargetExistsException {
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
