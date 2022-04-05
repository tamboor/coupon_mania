package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestMessage;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
@Order(5)
@RequiredArgsConstructor
public class GuestRestTest implements CommandLineRunner {
    private final RestTemplate restTemplate;
   // final CompanyRepo companyRepo;
    private final String GET_ALL_COUPONS_URI = "http://localhost:8080/guest/getAllCoupons";

    @Override
    public void run(String... args) throws Exception {
        try {
            getAllCoupons();
            System.out.println("This was guest in rest template");
        } catch (Exception e) {
e.printStackTrace();        }
    }


    private void getAllCoupons() throws Exception {
        var couponsJson = restTemplate.getForEntity(GET_ALL_COUPONS_URI, Coupon[].class);

      List<Coupon> coupons = Arrays.asList(couponsJson.getBody());

//        List<Coupon> coupons = Arrays.stream(couponsJson.getBody())
//                .map(c-> c.setCompany())
//
//                .collect(Collectors.toList());
        System.out.println(coupons);
    }

}
