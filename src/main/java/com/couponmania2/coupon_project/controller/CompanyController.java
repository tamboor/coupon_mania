package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.serialization.CouponForm;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.facade.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.DataTruncation;


@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController extends ClientController {

    private final CompanyServiceImpl companyService;
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
        if (!userDetails.roleCheck()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.ROLE_NOT_EXIST);
        }

        userDetails.setId(companyService.checkCredentials(
                        userDetails.getUserName(),
                        userDetails.getUserPass(),
                        ClientType.valueOf(userDetails.getRole()))
                .getId()
        );

        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    /**
     * adds a coupon to the database.
     *
     * @param token      authorization token.
     * @param couponForm form detailing the coupon that will be passed to the database.
     * @return ResponseEntity with HttpStatus and a new token.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     * @throws AppTargetExistsException        if the coupon already ecxists in the database.
     * @throws AppTargetNotFoundException      if the coupon has am id of a company that doesnt exist.
     * @throws AppInvalidInputException        if the input in the form is null or invalid.
     */
    @PostMapping("/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody CouponForm couponForm) throws AppUnauthorizedRequestException, AppTargetExistsException, AppInvalidInputException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);
        if (couponForm.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
        companyService.addCoupon(new Coupon(couponForm, companyService.getCompanyDetails(userDetails.getId())));
        return responseEntityGenerator.getResponseEntity(userDetails, HttpStatus.CREATED);
    }

    /**
     * updates a coupon in the database.
     *
     * @param token      authorization token.
     * @param couponForm form detailing the coupon that will be passed to the database.
     * @return ResponseEntity with HttpStatus and a new token.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     * @throws AppInvalidInputException        if the form has null fields or invalid input.
     * @throws AppTargetNotFoundException      if the coupon to update doesn't exist.
     */
    @PutMapping("/updateCoupon")
    private ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody CouponForm couponForm) throws AppUnauthorizedRequestException, AppInvalidInputException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);
        if (couponForm.checkNullFields()) {
            throw new AppInvalidInputException(AppInvalidInputMessage.NULL_FIELDS);
        }
        Coupon couponToUpdate = companyService.getCouponByID(couponForm.getId());
        couponToUpdate.setCategory(couponForm.getCategory());
        couponToUpdate.setTitle(couponForm.getTitle());
        couponToUpdate.setDescription(couponForm.getDescription());
        couponToUpdate.setAmount(couponForm.getAmount());
        couponToUpdate.setPrice(couponForm.getPrice());
        couponToUpdate.setImage(couponForm.getImage());
        couponToUpdate.setStartDate(couponForm.getStartDate());
        couponToUpdate.setEndDate(couponForm.getEndDate());
        companyService.updateCoupon(couponToUpdate);

        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    /**
     * deletes a coupon from the database.
     *
     * @param token    authorization token.
     * @param couponId the id of teh coupon to delete.
     * @return ResponseEntity with HttpStatus and a new token.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     * @throws AppTargetNotFoundException      if the coupon to delete was not found.
     * @throws AppInvalidInputException        if the coupon is not of given company
     */
    @DeleteMapping("/deleteCoupon/{couponId}")
    private ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable long couponId) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        UserDetails userDetails = validate(token);
        companyService.deleteCoupon(couponId, userDetails.getId());
        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    /**
     * gets all coupons from the database.
     *
     * @param token authorization token.
     * @return ResponseEntity with HttpStatus ,a new token and all of the coupons of the company.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     * @throws AppTargetNotFoundException      if company doesnt exist in the database.
     */
    @GetMapping("/getCompanyCoupons")
    private ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {

        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, companyService.getAllCompanyCoupons(userDetails.getId()));
    }

    /**
     * gets all coupons from the database filtered by category.
     *
     * @param token    authorization token.
     * @param category category to filter by.
     * @return ResponseEntity with HttpStatus ,a new token and all of the coupons of the company filtered by category.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     * @throws AppTargetNotFoundException      if company doesnt exist in the database.
     */
    @GetMapping("/couponsCategory/{category}")
    private ResponseEntity<?> getCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws AppUnauthorizedRequestException, AppTargetNotFoundException {

        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, companyService.getCompanyCouponsByCategory(userDetails.getId(), category));
    }

    /**
     * gets all coupons from the database filtered by max price..
     *
     * @param token    authorization token.
     * @param maxPrice max price to filter by.
     * @return ResponseEntity with HttpStatus ,a new token and all of the coupons of the company filtered by max price.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     * @throws AppTargetNotFoundException      if company doesnt exist in the database.
     * @throws AppInvalidInputException        if negative max price was entered.
     */
    @GetMapping("/couponsMaxPrice/{maxPrice}")
    private ResponseEntity<?> getCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, companyService.getCompanyCouponsByMaxPrice(userDetails.getId(), maxPrice));
    }

    /**
     * retrieves given company from the database.
     *
     * @param token authorization token.
     * @return the company that was found.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     * @throws AppTargetNotFoundException      if company wasnt found in the database.
     */
    @GetMapping("/getCompanyDetails")
    private ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails, companyService.getCompanyDetails(userDetails.getId()));
    }

    /**
     * validates auth token and retrieves associated userdetails.
     *
     * @param token authorization token.
     * @return the userdetails in the token,
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     */
    private UserDetails validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.company.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
        }
        return user;
    }

}



