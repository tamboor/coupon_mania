package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
//todo: add getAllCoupons
//todo: check all jwt shit
//todo: update login method,, replace ReaquestParams to PathVariables (change mapping too)
//todo: update POST methods (forms-IDs)
//todo:replace ReaquestParams to PathVariables (change mapping too)
public class CustomerController extends ClientController {
    private final CustomerServiceImpl customerService;
    private final JwtUtils jwtUtils;

    @Override
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails)
            throws AppUnauthorizedRequestException {
//
        userDetails.setId(customerService.checkCredentials(
                        userDetails.getUserName(),
                        userDetails.getUserPass(),
                        ClientType.valueOf(userDetails.getRole()))
                .getId()
        );
        return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);
    }

//todo: find out why it's mixing the ID numbers
    @PostMapping("/newPurchase")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestParam long couponId) throws AppUnauthorizedRequestException, AppTargetExistsException, AppTargetNotFoundException {
        long customerId = validate(token);
        customerService.purchaseCoupon(couponId, customerId );
    }

    //
    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        long customerId = validate(token);
        return new ResponseEntity<>(customerService.getCustomerCoupons(customerId), HttpStatus.OK);
    }

    @GetMapping("/getCustomerCoupons/category")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @RequestParam Category category) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        long customerId = validate(token);
        return new ResponseEntity<>(customerService.getCustomerCouponsByCategory(customerId, category), HttpStatus.OK);
    }

    @GetMapping("/getCustomerCoupons/maxPrice")
    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @RequestParam double maxPrice) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        long customerId = validate(token);
        return new ResponseEntity<>(customerService.getCustomerCouponsByMaxPrice(customerId, maxPrice), HttpStatus.OK);
    }
//todo: find why not working
    @GetMapping("/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        long customerId = validate(token);
        return new ResponseEntity<>(customerService.getCustomerDetails(customerId), HttpStatus.OK);
    }

    private long validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.customer.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
        }
        return user.getId();
    }
}
