package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppInvalidInputException;
import com.couponmania2.coupon_project.exceptions.AppInvalidInputMessage;
import com.couponmania2.coupon_project.exceptions.AppTargetExistsException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import com.couponmania2.coupon_project.serialization.CustomerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(maxAge = 3600)

@RestController
@RequestMapping("guest")
@RequiredArgsConstructor
public class GuestController {
    private final CustomerServiceImpl customerService;
    private final AdminServiceImpl adminService;
    private final JwtUtils jwtUtils;
    private final ResponseWithTokenProvider responseWithTokenProvider;

    /**
     * gets all coupons from the database.
     *
     * @return ResponseEntity containing HttpStatus, a new token and all the coupons in the database.
     */
    @GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(customerService.getAllCoupons(), HttpStatus.OK);
    }

    @GetMapping("/getDetails")
    public ResponseEntity<?>getUserDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = jwtUtils.validateToken(token);
        Map<String , String> details = new HashMap<>();
        details.put("role", userDetails.getRole());
        details.put("userName", userDetails.getUserName());
        return  responseWithTokenProvider.getResponseEntity(userDetails , details);
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register (@RequestBody CustomerForm customerForm) throws AppInvalidInputException, AppTargetExistsException {
        if (customerForm.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
//        System.out.println(customerForm);
        adminService.addCustomer(new Customer(customerForm));
//        return responseWithTokenProvider.getResponseEntity(userDetails,HttpStatus.CREATED);
//        return ResponseEntity<?> (HttpStatus.CREATED);
    }
}
