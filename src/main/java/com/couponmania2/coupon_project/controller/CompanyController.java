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
//todo: update login method,
// todo: replace ReaquestParams to PathVariables (change mapping too)
//todo: update POST methods (forms-IDs)
//todo:replace ReaquestParams to PathVariables (change mapping too)
public class CompanyController extends ClientController {

    private final CompanyServiceImpl companyService;
    private final JwtUtils jwtUtils;

    //TODO: try to put in abstract class (remove duplicate code)
    @Override
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails)
            throws AppUnauthorizedRequestException {
//        UserDetails user = UserDetails.builder()
//                .userName(userName)
//                .userPass(userPass)
//                .role(clientType.getName())
//                .id(companyService.checkCredentials(userName, userPass, clientType).getId())
//                .build();
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
    private void updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestParam long couponId, @RequestBody CouponForm couponForm) throws AppUnauthorizedRequestException, AppInvalidInputException, AppTargetNotFoundException {
        validate(token);
        Coupon couponToUpdate = companyService.getCouponByID(couponId);
        if (!couponForm.getCategory().getName().equals("")) {
            couponToUpdate.setCategory(couponForm.getCategory());
        }
        if (!couponForm.getTitle().equals("")) {
            couponToUpdate.setTitle(couponForm.getTitle());
        }
        if (!couponForm.getDescription().equals("")) {
            couponToUpdate.setDescription(couponForm.getDescription());
        }
        if (!(couponForm.getAmount() < 0)) {
            couponToUpdate.setAmount(couponForm.getAmount());
        }
        if (!(couponForm.getPrice() < 0)) {
            couponToUpdate.setPrice(couponForm.getPrice());
        }
        if (!couponForm.getImage().equals("")) {
            couponToUpdate.setImage(couponForm.getImage());
        }
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

    @GetMapping("/getCompanyCoupons/category")
    private ResponseEntity<?> getCouponsByCategory(@RequestHeader(name = "Authorization") String token, @RequestParam Category category) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(id, category), HttpStatus.OK);
    }

    @GetMapping("/getCompanyCoupons/maxPrice")
    private ResponseEntity<?> getCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @RequestParam double maxPrice) throws AppUnauthorizedRequestException, AppTargetNotFoundException, AppInvalidInputException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyCouponsByMaxPrice(id, maxPrice), HttpStatus.OK);
    }

    @GetMapping("/getCompanyDetails")
    private ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException, AppTargetNotFoundException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyDetails(id), HttpStatus.OK);
    }

    //TODO: put in abstract father
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
