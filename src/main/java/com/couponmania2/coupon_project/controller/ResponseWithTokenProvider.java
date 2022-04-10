package com.couponmania2.coupon_project.controller;

import com.couponmania2.coupon_project.auth.JwtUtils;
import com.couponmania2.coupon_project.auth.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseWithTokenProvider {
    private final JwtUtils jwtUtils;

    /**
     * returns a response entity with Bearer Authorization from specified UserDetails.
     * Defaults HttpStatus to OK and body to null.
     *
     * @param userDetails the UserDetails to create the JWT token from.
     * @return The ResponseEntity that was created.
     */
    public ResponseEntity<?> getResponseEntity(UserDetails userDetails) {
        return getResponseEntity(userDetails, HttpStatus.OK, null);
    }

    /**
     * returns a response entity with Bearer Authorization from specified userdetails and HttpStatus.
     * Defaults body to null.
     *
     * @param userDetails the UserDetails to create the JWT token from.
     * @param httpStatus  the HttpStatus to give the header.
     * @return The ResponseEntity that was created.
     */
    public ResponseEntity<?> getResponseEntity(UserDetails userDetails, HttpStatus httpStatus) {
        return getResponseEntity(userDetails, httpStatus, null);
    }

    //TODO: check change to generic

    /**
     * returns a response entity with Bearer Authorization from specified UserDetails and with given body and HttpStatus.
     *
     * @param userDetails the UserDetails to create the JWT token from.
     * @param httpStatus  the HttpStatus to give the header.
     * @param body        the body to put into the response.
     * @return The ResponseEntity that was created.
     */
    public ResponseEntity<?> getResponseEntity(UserDetails userDetails, HttpStatus httpStatus, Object body) {
        return ResponseEntity.status(httpStatus).headers(jwtUtils.getHeaderWithToken(userDetails)).body(body);
    }

    /**
     * returns a response entity with Bearer Authorization from specified userdetails and with given body.
     * Defaults HttpStatus to OK.
     *
     * @param userDetails the UserDetails to create the JWT token from.
     * @param body        the body to put into the response.
     * @return The ResponseEntity that was created.
     */
    public ResponseEntity<?> getResponseEntity(UserDetails userDetails, Object body) {
        return getResponseEntity(userDetails, HttpStatus.OK, body);
    }
}
