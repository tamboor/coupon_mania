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
public class CustomerController extends ClientController {
    private final CustomerServiceImpl customerService;
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

        if (userDetails.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
        userDetails.setRole(userDetails.getRole().toLowerCase());
        if (!userDetails.roleCheck()){
            throw new AppInvalidInputException(AppInvalidInputMessage.ROLE_NOT_EXIST);
        }

        userDetails.setId(customerService.checkCredentials(
                        userDetails.getUserName(),
                        userDetails.getUserPass(),
                        ClientType.valueOf(userDetails.getRole()))
                .getId()
        );
        return responseEntityGenerator.getResponseEntity(userDetails);
    }


    @PostMapping("/newPurchase")
//    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestParam long couponId) throws AppUnauthorizedRequestException, AppTargetExistsException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);
        customerService.purchaseCoupon(couponId, userDetails.getId());

        return responseEntityGenerator.getResponseEntity(userDetails, HttpStatus.CREATED);
    }

    @GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);

        return responseEntityGenerator.getResponseEntity(userDetails, customerService.getAllCoupons());
    }

    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);

        return responseEntityGenerator
                .getResponseEntity(userDetails, customerService.getCustomerCoupons(userDetails.getId()));
    }

    @GetMapping("/getCouponsByCategory/{category}")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);

        return responseEntityGenerator
                .getResponseEntity(userDetails, customerService.getCustomerCouponsByCategory(userDetails.getId(), category));
    }

    @GetMapping("/getCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        UserDetails userDetails = validate(token);

        return responseEntityGenerator
                .getResponseEntity(userDetails, customerService.getCustomerCouponsByMaxPrice(userDetails.getId(), maxPrice));
    }

    @GetMapping("/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);

        return responseEntityGenerator
                .getResponseEntity(userDetails, customerService.getCustomerDetails(userDetails.getId()));
    }

    private UserDetails validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.customer.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
        }
        return user;
    }
}
