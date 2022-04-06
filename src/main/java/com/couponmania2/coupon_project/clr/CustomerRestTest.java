package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundException;
import com.couponmania2.coupon_project.exceptions.AppTargetNotFoundMessage;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestMessage;
import com.couponmania2.coupon_project.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
//        try {
//            getAllCoupons();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try {
            getCustomerCoupons();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateTokenAndHeaders(ResponseEntity<?> response) {
        this.token = response.getHeaders().getFirst("authorization");
        this.headers = new HttpHeaders();
        headers.set("authorization", token);
    }

    private HttpEntity<?> getHttpEntity(Object data) {
        if (data != null) {
            return new HttpEntity<>(data, headers);
        } else {
            return new HttpEntity<>(headers);
        }
    }

    private void checkResponse(ResponseEntity<?> response) throws Exception {
        if (response.getStatusCode().is4xxClientError()) {
            throw new Exception("Client Error: " + response.getStatusCode().name());

        }
        if (response.getStatusCode().is5xxServerError()) {
            throw new Exception("Server Error: " + response.getStatusCode().name());
        }
    }

    private void login(UserDetails userDetails) throws Exception {

        ResponseEntity<?> response = restTemplate.exchange(LOGIN_URI,
                HttpMethod.POST,
                new HttpEntity<>(userDetails),
                Void.class);
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("customer login was successful via rest template ");
        System.out.println(token);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void getAllCoupons() throws Exception {

        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_ALL_COUPONS_URI,
                HttpMethod.GET, getHttpEntity(null), Coupon[].class);
        checkResponse(response);
        List<Coupon> coupons = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        coupons.forEach(System.out::println);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void getCustomerCoupons() throws Exception {

        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_CUSTOMER_COUPONS_URI,
                HttpMethod.GET, getHttpEntity(null), Coupon[].class);
        checkResponse(response);
        List<Coupon> coupons = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(coupons);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
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
