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


@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController extends ClientController {
    private final AdminServiceImpl adminService;
    private final JwtUtils jwtUtils;


    @Override
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String userPass, @RequestParam ClientType clientType)
            throws AppUnauthorizedRequestException {
        UserDetails user = UserDetails.builder()
                .userName(userName)
                .userPass(userPass)
                .role(clientType.getName())
                .id(adminService.checkCredentials(userName, userPass, clientType))
                .build();
        return new ResponseEntity<>(jwtUtils.generateToken(user), HttpStatus.OK);
    }

    @PostMapping("/addCompany")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody CompanyForm companyForm) throws AppUnauthorizedRequestException, AppTargetExistsException {
        validate(token);
        adminService.addCompany(new Company(companyForm));

    }

    @PutMapping("/updateCompany")
    @ResponseStatus(HttpStatus.OK)
    public void updateCompany(@RequestHeader(name = "Authorization") String token, @RequestParam long id, @RequestBody CompanyForm companyForm) throws AppTargetNotFoundException, AppUnauthorizedRequestException, AppInvalidInputException {
        validate(token);
        Company company = adminService.getOneCompany(id);
        company.setName(companyForm.getName());
        company.setEmail(companyForm.getEmail());
        company.setPassword(company.getPassword());
        adminService.updateCompany(company);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        validate(token);
        adminService.deleteCompany(companyId);
    }


    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        validate(token);
        return new ResponseEntity<>(adminService.getAllComapnies(), HttpStatus.OK);
    }

    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        validate(token);
        return new ResponseEntity<>(adminService.getOneCompany(companyId), HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody CustomerForm customerForm) throws AppUnauthorizedRequestException, AppTargetExistsException {
        validate(token);
        adminService.addCustomer(new Customer(customerForm));
    }

    @PutMapping("/updateCustomer")
    public void updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestParam long id, @RequestBody CustomerForm customerForm) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        validate(token);

        Customer customerToUpdate = adminService.getOneCustomer(id);
        customerToUpdate.setFirstName(customerForm.getFirstName());
        customerToUpdate.setLastName(customerForm.getLastName());
        customerToUpdate.setEmail(customerForm.getEmail());
        customerToUpdate.setPassword(customerForm.getPassword());

        adminService.updateCustomer(customerToUpdate);
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        validate(token);
        adminService.deleteCustomer(customerId);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomer(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        validate(token);
        return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        validate(token);
        return new ResponseEntity<>(adminService.getOneCustomer(customerId), HttpStatus.OK);
    }

    private void validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.ADMIN.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
    }


}
