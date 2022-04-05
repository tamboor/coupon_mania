package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundMessage;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

//@Component
@Order(4)
@RequiredArgsConstructor

public class CustomerRestTest implements CommandLineRunner {
    private final RestTemplate restTemplate;
    private final String LOGIN_URI = "http://localhost:8080/customer/login";
    private final String PURCHASE_COUPON_URI = "http://localhost:8080/customer/newPurchase?couponId={id}";
    private final String GET_ALL_COUPONS_URI = "http://localhost:8080/customer/getAllCoupons";
    private final String GET_CUSTOMER_COUPONS_URI = "http://localhost:8080/customer/getCustomerCoupons";
    private final String GET_COUPONS_BY_MAX_PRICE_URI = "http://localhost:8080/customer/getCouponsByMaxPrice/{maxPrice}";
    private final String GET_COUPONS_BY_CATEGORY_URI = "http://localhost:8080/customer/getCouponsByCategory/{category}";
    private final String GET_CUSTOMER_DETAILS_URI = "http://localhost:8080/customer/getCustomerDetails";

    private HttpEntity<?> httpEntity;
    private HttpHeaders headers;
    private String token;

    @Override
    public void run(String... args) throws Exception {

        try {
            login(UserDetails.builder().userPass("pass").userName("mail1").role("customer").build());
            System.out.println("Login was successful via rest template");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(AppUnauthorizedRequestMessage.NO_LOGIN.getMessage());
        }

        getAllCoupons();
    }


    private void login(UserDetails userDetails) throws Exception {

        ResponseEntity<?> responseToken = restTemplate.exchange(LOGIN_URI,
                HttpMethod.POST,
                new HttpEntity<>(userDetails),
                String.class);
        if (responseToken.getStatusCode().is4xxClientError()){
            throw new Exception("Client Error: "+ responseToken.getStatusCode().name());
        }
        if (responseToken.getStatusCode().is5xxServerError()){
            throw new Exception("Server Error: "+ responseToken.getStatusCode().name());
        }
        this.token = responseToken.getBody().toString();
        this.headers = new HttpHeaders();
        headers.set("Authorization", token);
        this.httpEntity = new HttpEntity<>(headers);
        System.out.println(token);
    }

    private void getAllCoupons() throws Exception {

        Set c = restTemplate.exchange(GET_ALL_COUPONS_URI,
                HttpMethod.GET, httpEntity, Set.class).getBody();
        System.out.println(c);
    }

    private void getCustomerCoupons() throws Exception {

        Set c = restTemplate.exchange(GET_CUSTOMER_COUPONS_URI,
                HttpMethod.GET, httpEntity, Set.class).getBody();
        System.out.println(c);
    }

    private void getCouponsByCategory(Category category) throws Exception {
        Map<String, Category> params = new HashMap<>();
        params.put("category", category);
        Set c = restTemplate.exchange(GET_COUPONS_BY_CATEGORY_URI,
                        HttpMethod.GET, httpEntity, Set.class, params)
                .getBody();
        System.out.println(c);
    }

    private void getCouponsByMaxPrice(double price) throws Exception {
        Map<String, Double> params = new HashMap<>();
        params.put("price", price);
        Set c = restTemplate.exchange(GET_COUPONS_BY_MAX_PRICE_URI,
                        HttpMethod.GET, httpEntity, Set.class, params)
                .getBody();
        System.out.println(c);
    }

    private void purchaseCoupon(long id) throws Exception {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        var object = restTemplate.exchange(PURCHASE_COUPON_URI,
                HttpMethod.POST, httpEntity, Set.class, params);


    }

    private void getCustomerDetails() throws Exception {

        Optional<Company> c = restTemplate.exchange(GET_CUSTOMER_DETAILS_URI,
                HttpMethod.GET, httpEntity, Optional.class, new HashMap<>()).getBody();

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(c.get());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }
}
