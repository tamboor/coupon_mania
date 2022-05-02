package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(maxAge = 3600)

@RestController
@RequestMapping("guest")
@RequiredArgsConstructor
public class GuestController {
    private final CustomerServiceImpl customerService;
    private final JwtUtils jwtUtils;
    private final ResponseWithTokenProvider responseWithTokenProvider;

    /**
     * gets all coupons from the database.
     *
     * @return ResponseEntity containing HttpStatus, a new token and all the coupons in the database.
     */
    @GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(customerService.getAllCoupons(), HttpStatus.OK);
    }

    @GetMapping("/getDetails")
    public ResponseEntity<?>getUserDetails(@RequestHeader(name = "Authorization") String token) throws AppUnauthorizedRequestException {
        UserDetails userDetails = jwtUtils.validateToken(token);
        Map<String , String> details = new HashMap<>();
        details.put("role", userDetails.getRole());
        details.put("userName", userDetails.getUserName());
        return  responseWithTokenProvider.getResponseEntity(userDetails , details);
    }
}
