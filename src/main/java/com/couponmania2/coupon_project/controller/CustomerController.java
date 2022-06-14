package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import com.couponmania2.coupon_project.serialization.CouponForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(maxAge = 3600)

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class CustomerController implements AuthenticatedController {
    private final CustomerServiceImpl customerService;
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
//    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails)
            throws AppUnauthorizedRequestException, AppInvalidInputException {

        if (userDetails.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
        userDetails.setRole(userDetails.getRole().toLowerCase());
        if (!userDetails.roleCheck()) {

            throw new AppInvalidInputException(AppInvalidInputMessage.ROLE_NOT_EXIST);
        }
        userDetails.setId(customerService.checkCredentials(
                        userDetails.getUserName(),
                        userDetails.getUserPass(),
                        ClientType.valueOf(userDetails.getRole()))
                .getId()
        );
        return responseWithTokenProvider.getResponseEntity(userDetails);
    }

    /**
     * adds a coupon purchase to the database.
     *
     * @param token    authorization token.
     * @param coupon the coupon the customer wishes to buy.
     * @return ResponseEntity containing HttpStatus and a new token.
     * @throws AppUnauthorizedRequestException if authorization is expired or failed.
     * @throws AppTargetExistsException        if the customer already has a coupon of this type.
     * @throws AppTargetNotFoundException      if couponid doesnt exist in the database.
     */
    @PostMapping("/newPurchase")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody CouponForm coupon) throws AppUnauthorizedRequestException, AppTargetExistsException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);
        customerService.purchaseCoupon(coupon.getId(), userDetails.getId());

        return responseWithTokenProvider.getResponseEntity(userDetails, HttpStatus.CREATED);
    }

    /**
     * gets all coupons from the database.
     *
     * @param token authorization token.
     * @return ResponseEntity containing HttpStatus, a new token and all the coupons in the database.
     * @throws AppUnauthorizedRequestException if authorization is expired or failed.
     */
    @GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = validate(token);

        return responseWithTokenProvider.getResponseEntity(userDetails, customerService.getAllCoupons());
    }

    /**
     * gets all the coupons the customer owns.
     *
     * @param token authorization token.
     * @return ResponseEntity containing HttpStatus, a new token and all the coupons the customer owns.
     * @throws AppUnauthorizedRequestException if authorization is expired or failed.
     * @throws AppTargetNotFoundException      if the customer doesnt exist in the database.
     */
    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);

        return responseWithTokenProvider
                .getResponseEntity(userDetails, customerService.getCustomerCoupons(userDetails.getId()));
    }

    /**
     * gets all the coupons the customer owns filtered by category.
     *
     * @param token    authorization token.
     * @param category the category to filter by.
     * @return ResponseEntity containing HttpStatus, a new token and all the coupons the customer owns filtered category.
     * @throws AppUnauthorizedRequestException if authorization is expired or failed.
     * @throws AppTargetNotFoundException      if the customer doesnt exist in the database.
     */
    @GetMapping("/getCouponsByCategory/{category}")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);

        return responseWithTokenProvider
                .getResponseEntity(userDetails, customerService.getCustomerCouponsByCategory(userDetails.getId(), category));
    }

    /**
     * gets all the coupons the customer owns filtered by category.
     *
     * @param token    authorization token.
     * @param maxPrice max price to filter by.
     * @return ResponseEntity containing HttpStatus, a new token and all the coupons the customer owns filtered max price.
     * @throws AppUnauthorizedRequestException if authorization is expired or failed.
     * @throws AppTargetNotFoundException      if the customer doesnt exist in the database.
     * @throws AppInvalidInputException        if entered negative price.
     */
    @GetMapping("/getCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        UserDetails userDetails = validate(token);

        return responseWithTokenProvider
                .getResponseEntity(userDetails, customerService.getCustomerCouponsByMaxPrice(userDetails.getId(), maxPrice));
    }

    /**
     * retrieves a customer from the database.
     *
     * @param token authorization token.
     * @return ResponseEntity containing HttpStatus, a new token and the customer found.
     * @throws AppUnauthorizedRequestException if authorization is expired or failed.
     * @throws AppTargetNotFoundException      if the customer doesnt exist in the database.
     */
    @GetMapping("/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);

        return responseWithTokenProvider
                .getResponseEntity(userDetails, customerService.getCustomerDetails(userDetails.getId()));
    }
    @GetMapping("/checkCoupon/{id}")
    public ResponseEntity<?> checkCoupon(@RequestHeader(name = "Authorization") String token , @PathVariable long id) throws AppUnauthorizedRequestException, AppTargetExistsException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);

        return responseWithTokenProvider
                .getResponseEntity(userDetails , customerService.validateCoupon(id , userDetails.getId()));
    }

    /**
     * validates auth token and retrieves associated userdetails.
     *
     * @param token authorization token.
     * @return the userdetails in the token,1
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     */
    private UserDetails validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.customer.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
        }
        return user;
    }


}
