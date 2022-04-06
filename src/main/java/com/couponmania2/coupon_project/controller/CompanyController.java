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

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController extends ClientController {

    private final CompanyServiceImpl companyService;
    private final JwtUtils jwtUtils;
    private final ResponseWithTokenProvider responseEntityGenerator;
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
//        return  ResponseEntity.ok().headers(jwtUtils.getHeaderWithToken(userDetails)).body(null);
        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    @PostMapping("/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody CouponForm couponForm) throws AppUnauthorizedRequestException, AppTargetExistsException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);
        companyService.addCoupon(new Coupon(couponForm, companyService.getCompanyDetails(userDetails.getId())));
        return responseEntityGenerator.getResponseEntity(userDetails , HttpStatus.CREATED);
    }

    @PutMapping("/updateCoupon")
    private ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody CouponForm couponForm) throws AppUnauthorizedRequestException, AppInvalidInputException, AppTargetNotFoundException {
       UserDetails userDetails= validate(token);
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

    @DeleteMapping("/deleteCoupon/{couponId}")
//    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable long couponId) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        UserDetails userDetails = validate(token);
        companyService.deleteCoupon(couponId, userDetails.getId());
        return responseEntityGenerator.getResponseEntity(userDetails);
    }

    @GetMapping("/getCompanyCoupons")
    private ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);
//        return  ResponseEntity.ok()
//                .headers(jwtUtils.getHeaderWithToken(userDetails))
//                .body(companyService.getAllCompanyCoupons(userDetails.getId()));

        return responseEntityGenerator.getResponseEntity(userDetails , companyService.getAllCompanyCoupons(userDetails.getId()));
    }

    @GetMapping("/couponsCategory/{category}")
    private ResponseEntity<?> getCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
         UserDetails userDetails = validate(token);
//        return ResponseEntity.ok()
//                .headers(jwtUtils.getHeaderWithToken(userDetails))
//                .body(companyService.getCompanyCouponsByCategory(userDetails.getId(), category));
        return responseEntityGenerator.getResponseEntity(userDetails , companyService.getCompanyCouponsByCategory(userDetails.getId(), category));
    }

    @GetMapping("/couponsMaxPrice/{maxPrice}")
    private ResponseEntity<?> getCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        UserDetails userDetails = validate(token);
//        return  ResponseEntity.ok()
//                .headers(jwtUtils.getHeaderWithToken(userDetails))
//                .body(companyService.getCompanyCouponsByMaxPrice(userDetails.getId(), maxPrice));

        return responseEntityGenerator.getResponseEntity(userDetails , companyService.getCompanyCouponsByMaxPrice(userDetails.getId(), maxPrice));

    }

    @GetMapping("/getCompanyDetails")
    private ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        UserDetails userDetails = validate(token);
        return responseEntityGenerator.getResponseEntity(userDetails , companyService.getCompanyDetails(userDetails.getId()));

    }

    private UserDetails validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.company.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
        }
        return user;
    }

//    private Company validateForObject(String token) throws AppUnauthorizedRequestException {
//        try {
//            System.out.println(companyService.getCompanyDetails(validate(token).getId()));
//            return companyService.getCompanyDetails(validate(token).getId());
//        } catch (AppTargetNotFoundException err) {
//            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS);
//        }
    }



