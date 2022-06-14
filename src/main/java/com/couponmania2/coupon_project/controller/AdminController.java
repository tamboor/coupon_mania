package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.facade.AdminService;
import com.couponmania2.coupon_project.serialization.CompanyForm;
import com.couponmania2.coupon_project.serialization.CustomerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class AdminController implements AuthenticatedController {
    private final AdminService adminService;
    private final JwtUtils jwtUtils;
    private final ResponseWithTokenProvider responseWithTokenProvider;

    /**
     * tries to login an admin user.
     *
     * @param userDetails the details of the user.
     * @return response entity that holds a token and a response status.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     */
    @Override
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails)
            throws AppUnauthorizedRequestException, AppInvalidInputException {

        if (userDetails.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
        userDetails.setRole(userDetails.getRole().toLowerCase());
        if (!userDetails.roleCheck()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.ROLE_NOT_EXIST);
        }

        userDetails.setId(adminService.checkCredentials(
                userDetails.getUserName(),
                userDetails.getUserPass(),
                ClientType.valueOf(userDetails.getRole())));

        return responseWithTokenProvider.getResponseEntity(userDetails);

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
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody CompanyForm companyForm) throws AppUnauthorizedRequestException, AppTargetExistsException, AppInvalidInputException {
        UserDetails userDetails = validate(token);
        if (companyForm.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
        adminService.addCompany(new Company(companyForm));
        return responseWithTokenProvider.getResponseEntity(userDetails, HttpStatus.CREATED);
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
        if (companyForm.checkNullFields()) {

            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
        Company companyToUpdate = adminService.getOneCompany(companyForm.getId());
        companyToUpdate.setEmail(companyForm.getEmail());
        companyToUpdate.setPassword(companyForm.getPassword());
        companyToUpdate.setName(companyForm.getName());

        adminService.updateCompany(companyToUpdate);

        return responseWithTokenProvider.getResponseEntity(userDetails);
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

        return responseWithTokenProvider.getResponseEntity(userDetails);
    }

    /**
     * @param token authentication token.
     * @return response entity with httpstatus and new token.
     * @throws AppUnauthorizedRequestException if authentication failed.
     */
    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);

        return responseWithTokenProvider.getResponseEntity(userDetails, adminService.getAllCompanies());
    }

    /**
     * Retrieves one company from the database.
     *
     * @param token     authentication token.
     * @param companyId company to retrieves.
     * @return response entity containing the httpresponse, auth token and the company (serialized and in body.).
     * @throws AppTargetNotFoundException      if failed to find company.
     * @throws AppUnauthorizedRequestException if authentication failed.
     */
    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseWithTokenProvider.getResponseEntity(userDetails, adminService.getOneCompany(companyId));
    }

    /**
     * @param token     authentication token.
     * @param companyId company to get the coupons of.
     * @return response entity containing httpstatus, new token and the coupons of the specified company.
     * @throws AppTargetNotFoundException      if failed to find company.
     * @throws AppUnauthorizedRequestException if authentication failed.
     */
    @GetMapping("/getCompanyCoupons/{companyId}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseWithTokenProvider.getResponseEntity(userDetails, adminService.getCompanyCoupons(companyId));
    }

    /**
     * adds a customer to the database.
     *
     * @param token        authentication token.
     * @param customerForm form representing the details of the customer to add to the database.
     * @return response entity containing httpstatus and new token.
     * @throws AppUnauthorizedRequestException if authentication failed.
     * @throws AppTargetExistsException        if the customer already exists in teh database.
     * @throws AppInvalidInputException        if the CustomerForm has null fields (invalid input)
     */
    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody CustomerForm customerForm) throws AppUnauthorizedRequestException, AppTargetExistsException, AppInvalidInputException {
        UserDetails userDetails = validate(token);
        if (customerForm.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
        adminService.addCustomer(new Customer(customerForm));
        return responseWithTokenProvider.getResponseEntity(userDetails,HttpStatus.CREATED);

    }

    /**
     * Updates the details of a customer in the database.
     *
     * @param token        Authentication token.
     * @param customerForm Form representing the details of the customer to update in the database.
     * @return ResponseEntity with HttpStatus and new token.
     * @throws AppTargetNotFoundException      if the target customer was not found.
     * @throws AppUnauthorizedRequestException if authentication failed.
     * @throws AppTargetExistsException        if trying to update to existing email.
     * @throws AppInvalidInputException        if the customer form has any null fields.
     */
    @PutMapping("/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody CustomerForm customerForm) throws AppTargetNotFoundException, AppUnauthorizedRequestException, AppTargetExistsException, AppInvalidInputException {
        UserDetails userDetails = validate(token);
        if (customerForm.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }

        Customer customerToUpdate = adminService.getOneCustomer(customerForm.getId());
        customerToUpdate.setEmail(customerForm.getEmail());
        customerToUpdate.setFirstName(customerForm.getFirstName());
        customerToUpdate.setLastName(customerForm.getLastName());
        customerToUpdate.setPassword(customerForm.getPassword());
        adminService.updateCustomer(customerToUpdate);

        return responseWithTokenProvider.getResponseEntity(userDetails);
    }

    /**
     * delete a customer from the database.
     *
     * @param token      authorization token.
     * @param customerId the id of the customer to delete.
     * @return response entity with httpstatus and new token.
     * @throws AppTargetNotFoundException      if no customer with given id exists.
     * @throws AppUnauthorizedRequestException if authentication failed.
     */
    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        adminService.deleteCustomer(customerId);
        return responseWithTokenProvider.getResponseEntity(userDetails);
    }

    /**
     * retrieves all customers from teh database.
     *
     * @param token authorization token.
     * @return ResponseEntity with HttpStatus,new token and the customers in the database.
     * @throws AppUnauthorizedRequestException if authentication failed.
     */
    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomer(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseWithTokenProvider.getResponseEntity(userDetails, adminService.getAllCustomers());
    }

    /**
     * retrieves one customer from the database.
     *
     * @param token      authorization token.
     * @param customerId the id of the customer to find.
     * @return the customer found.
     * @throws AppTargetNotFoundException      if customer was not found in teh database.
     * @throws AppUnauthorizedRequestException if authentication failed.
     */
    @GetMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseWithTokenProvider.getResponseEntity(userDetails, adminService.getOneCustomer(customerId));
    }

    /**
     * gets the coupons of given customer.
     *
     * @param token      authorization token.
     * @param customerId the id of the customer to find the coupons of.
     * @return ResponseEntity with HttpStatus ,  new token and a list of the coupons of the customer.
     * @throws AppTargetNotFoundException      if the customer was not found in the database.
     * @throws AppUnauthorizedRequestException if authentication failed.
     */
    @GetMapping("/getCustomerCoupons/{customerId}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);
        return responseWithTokenProvider.getResponseEntity(userDetails, adminService.getCustomerCoupons(customerId));
    }

    @GetMapping("getCustomerByEmail/{email}")
    public ResponseEntity<?> getCustomerByEmail(@RequestHeader(name = "Authorization") String token , @PathVariable String email) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails  = validate(token);

        return responseWithTokenProvider.getResponseEntity(userDetails, adminService.getCustomerByEmail(email));
    }

    @GetMapping("getCompanyByEmail/{email}")
    public ResponseEntity<?> getCompanyByEmail(@RequestHeader(name = "Authorization") String token , @PathVariable String email) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails  = validate(token);

        return responseWithTokenProvider.getResponseEntity(userDetails, adminService.getCompanyByEmail(email));
    }

    /**
     * Checks a token and makes sure its valid, retrieves the userdetails stored in the token.
     *
     * @param token the token to validate.
     * @return the userdetails stored in the token.
     * @throws AppUnauthorizedRequestException if authentication
     */
    private UserDetails validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.admin.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return user;
    }


}
