package com.couponmania2.coupon_project.controller;


import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class ClientController {
    /**
     * tries to login a user..
     * @param userDetails the details of the user.
     * @return response entity that holds a token and a response status.
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     */
    public abstract ResponseEntity<?> login( UserDetails userDetails)
            throws AppUnauthorizedRequestException;
}
