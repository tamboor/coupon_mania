package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestMessage;
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

public class CustomerController extends ClientController {
    private final CustomerServiceImpl customerService;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String userPass, @RequestParam ClientType clientType)
            throws AppUnauthorizedRequestException {
        UserDetails user = UserDetails.builder()
                .userName(userName)
                .userPass(userPass)
                .role(clientType.getName())
                .id(customerService.checkCredentials(userName, userPass, clientType))
                .build();
        return new ResponseEntity<>(jwtUtils.generateToken(user), HttpStatus.OK);
    }


    @PostMapping("/newPurchase")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws AppUnauthorizedRequestException {
        long customerId = validate(token);
        customerService.purchaseCoupon(customerId, coupon.getId());
    }

    //
    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        long customerId = validate(token);
        return new ResponseEntity<>(customerService.getCustomerCoupons(customerId), HttpStatus.OK);
    }

    @GetMapping("/getCustomerCoupons/category")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @RequestBody Category category) throws AppUnauthorizedRequestException {
        long customerId = validate(token);
        return new ResponseEntity<>(customerService.getCustomerCouponsByCategory(customerId, category), HttpStatus.OK);
    }

    @GetMapping("/getCustomerCoupons/maxPrice")
    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws AppUnauthorizedRequestException {
        long customerId = validate(token);
        return new ResponseEntity<>(customerService.getCustomerCouponsByMaxPrice(customerId, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/getCustomerDetails/?")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        long customerId = validate(token);
        return new ResponseEntity<>(customerService.getCustomerDetails(customerId), HttpStatus.OK);
    }

    private long validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.CUSTOMER.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return user.getId();
    }
}
