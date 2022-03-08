package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

//todo: check how verify token by params and not only by token signature.(user name, role, exp. date etc.)


@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController extends ClientController {
    private final AdminServiceImpl adminService;
    private final JwtUtils jwtUtils;


//    @Override
//    @PostMapping("login")
//    //todo: check if we can use @RequestParam instead of @RequestBody
//    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws AppUnauthorizedRequestException {
//        userDetails.setId(adminService.checkCredentials(userDetails.getUserName(), userDetails.getUserPass()));
//        return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);
//    }
@Override
@PostMapping("login")
public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String userPass, @RequestParam ClientType clientType)
        throws AppUnauthorizedRequestException {
//    userDetails.setId(adminService.checkCredentials(userDetails.getUserName(), userDetails.getUserPass()));
    UserDetails user = UserDetails.builder()
            .userName(userName)
            .userPass(userPass)
            .role(clientType.getName())
            .id(adminService.checkCredentials(userName,userPass))
            .build();
    return new ResponseEntity<>(jwtUtils.generateToken(user), HttpStatus.OK);
}

    @PostMapping("/addCompany")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws Exception {
        UserDetails user = jwtUtils.validateToken(token);
        if (user.getRole().equals(ClientType.Admin.getName())){
            adminService.addCompany(company);
        }
    }

    @PutMapping("/updateCompany")
    @ResponseStatus(HttpStatus.OK)
    public void updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        adminService.updateCompany(company);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        adminService.deleteCompany(companyId);
    }


    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        return new ResponseEntity<>(adminService.getAllComapnies(), HttpStatus.OK);
    }

    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable long companyId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        System.out.println(user.getUserName()+"FUCKFUCKFUCKFUCKFUCKFUCK");
        return new ResponseEntity<>(adminService.getOneCompany(companyId), HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws AppUnauthorizedRequestException, AppTargetExistsException {
        //todo: change exception
        UserDetails user = jwtUtils.validateToken(token);
        adminService.addCustomer(customer);
    }

    @PutMapping("/updateCustomer")
    public void updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        adminService.updateCustomer(customer);
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        adminService.deleteCustomer(customerId);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomer(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable long customerId) throws AppTargetNotFoundException, AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        return new ResponseEntity<>(adminService.getOneCustomer(customerId), HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneCustomer(customerId));
//        return new ResponseEntity<Customer>(HttpStatus.);

    }


}
