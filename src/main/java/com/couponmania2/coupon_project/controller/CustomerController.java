package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
//todo: add getAllCoupons
public class CustomerController extends ClientController {
    private final CustomerServiceImpl customerService;
    private final JwtUtils jwtUtils;

    @Override
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String userPass, @RequestParam ClientType clientType)
            throws AppUnauthorizedRequestException {
        UserDetails user = UserDetails.builder()
                .userName(userName)
                .userPass(userPass)
                .role(clientType.getName())
                .id(customerService.checkCredentials(userName, userPass, clientType).getId())
                .build();
        return new ResponseEntity<>(jwtUtils.generateToken(user), HttpStatus.OK);
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
        if (!(user.getRole().equals(ClientType.CUSTOMER.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
        }
        return user.getId();
    }
}
