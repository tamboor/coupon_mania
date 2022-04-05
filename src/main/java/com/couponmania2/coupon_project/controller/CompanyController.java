package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.serialization.CouponForm;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.facade.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
//todo: check all jwt shit
public class CompanyController extends ClientController {

    private final CompanyServiceImpl companyService;
    private final JwtUtils jwtUtils;
    /**
     * tries to login an admin user.
     * @param userDetails the details of the user.
     * @return response entity that holds a token and a response status.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     */
    @Override
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails)
            throws AppUnauthorizedRequestException, AppInvalidInputException {
        if (!userDetails.getRole().equals(ClientType.company.getName())){
            throw new AppInvalidInputException("Bad role input.");
        }

        userDetails.setId(companyService.checkCredentials(
                userDetails.getUserName(),
                userDetails.getUserPass(),
                  ClientType.valueOf(userDetails.getRole()))
                .getId()
        );
        return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);
    }

    @PostMapping("/addCoupon")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody CouponForm couponForm) throws AppUnauthorizedRequestException, AppTargetExistsException {
//        long id = validate(token);
//
//        Company company = null;
//
//        try {
//             company = companyService.getCompanyDetails(id);
//        } catch (AppTargetNotFoundException e) {
//            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.NO_LOGIN);
//        }

        companyService.addCoupon(new Coupon(couponForm, validateForObject(token)));
    }

    @PutMapping("/updateCoupon")
    @ResponseStatus(HttpStatus.OK)
    private void updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody CouponForm couponForm) throws AppUnauthorizedRequestException, AppInvalidInputException, AppTargetNotFoundException {
        validate(token);
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
    }

    @DeleteMapping("/deleteCoupon/{couponId}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable long couponId) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        long id = validate(token);
        companyService.deleteCoupon(couponId, id);
    }

    @GetMapping("/getCompanyCoupons")
    private ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getAllCompanyCoupons(id), HttpStatus.OK);
    }

    @GetMapping("/couponsCategory/{category}")
    private ResponseEntity<?> getCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(id, category), HttpStatus.OK);
    }

    @GetMapping("/couponsMaxPrice/{maxPrice}")
    private ResponseEntity<?> getCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyCouponsByMaxPrice(id, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/getCompanyDetails")
    private ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyDetails(id), HttpStatus.OK);
    }

    private long validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.company.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
        }
        return user.getId();
    }

    private Company validateForObject(String token) throws AppUnauthorizedRequestException {
        try {
            return companyService.getCompanyDetails(validate(token));
        } catch (AppTargetNotFoundException err) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
        }
    }


}
