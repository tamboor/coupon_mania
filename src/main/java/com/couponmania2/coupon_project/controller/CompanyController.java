package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestMessage;
import com.couponmania2.coupon_project.facade.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor

//todo: add jwt and update methods accordingly.
public class CompanyController extends ClientController {

    private final CompanyServiceImpl companyService;
    private final JwtUtils jwtUtils;

    //TODO: try to put in abstract class (remove duplicate code)
    @Override
    public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String userPass, @RequestParam ClientType clientType)
            throws AppUnauthorizedRequestException {
        UserDetails user = UserDetails.builder()
                .userName(userName)
                .userPass(userPass)
                .role(clientType.getName())
                .id(companyService.findByLoginCredentials(userName , userPass , clientType).getId())
                .build();

        return new ResponseEntity<>(jwtUtils.generateToken(user), HttpStatus.OK);
    }

    @PostMapping("/addCoupon")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon(@RequestBody Coupon coupon) {
        companyService.addCoupon(coupon);
    }

    @PutMapping("/updateCoupon")
    @ResponseStatus(HttpStatus.OK)
    private void updateCoupon(@RequestBody Coupon coupon) throws Exception {
        companyService.updateCoupon(coupon);
    }

    @DeleteMapping("/deleteCoupon/{couponId}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteCoupon (@RequestHeader(name = "Authorization") String token, @PathVariable long couponId) throws AppUnauthorizedRequestException {
        long id = validate(token);
        companyService.deleteCoupon(couponId , id);
    }

    @GetMapping("/getCompanyCoupons")
    private ResponseEntity<?> getAllCoupons (@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getAllCompanyCoupons(id),HttpStatus.OK);
    }

    @GetMapping("/getCompanyCoupons/category")
    private ResponseEntity<?> getCouponsByCategory (@RequestHeader(name = "Authorization") String token , @PathVariable Category category) throws AppUnauthorizedRequestException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(id, category),HttpStatus.OK);
    }

    @GetMapping("/getCompanyCoupons/maxPrice")
    private ResponseEntity<?> getCouponsByMaxPrice (@RequestHeader(name = "Authorization") String token ,@PathVariable double maxPrice) throws AppUnauthorizedRequestException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyCouponsByMaxPrice(id, maxPrice),HttpStatus.OK);
    }

    @GetMapping("/getCompanyDetails")
    private ResponseEntity<?> getCompanyDetails (@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        long id = validate(token);
        return new ResponseEntity<>(companyService.getCompanyDetails(id),HttpStatus.OK);
    }

    private long validate(String token) throws AppUnauthorizedRequestException {
        UserDetails user = jwtUtils.validateToken(token);
        if (!(user.getRole().equals(ClientType.Company.getName()))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.BAD_CREDENTIALS.getMessage());
        }
        return user.getId();
    }


}
