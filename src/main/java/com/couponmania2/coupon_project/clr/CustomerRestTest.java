package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
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

@Component
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

    private HttpHeaders headers;
    private String token;

    @Override
    public void run(String... args) throws Exception {

        //region customer login:
        try {
            System.out.println("=====================================================================");
            System.out.println("Customer: Login via rest template:");
            System.out.println("=====================================================================");
            login(UserDetails.builder().userPass("pass").userName("mail1").role("customer").build());
            System.out.println("Login was successful via rest template");
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region get all system's coupons:
        try {
            System.out.println("=====================================================================");
            System.out.println("Customer: get all coupons via rest template:");
            System.out.println("=====================================================================");
            getAllCoupons();
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion get all customer's coupons

        //region get all customer's coupons:
        try {
            System.out.println("=====================================================================");
            System.out.println("Customer: get all customer's coupons via rest template:");
            System.out.println("=====================================================================");
            getCustomerCoupons();
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region get all customer's coupons by category:
        try {
            System.out.println("=====================================================================");
            System.out.println("Customer: get customer's coupons by category via rest template:");
            System.out.println("=====================================================================");
            getCouponsByCategory(Category.xtreme);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region get all customer's coupons by max price:
        try {
            System.out.println("=====================================================================");
            System.out.println("Customer: get customer's coupons by max price via rest template:");
            System.out.println("=====================================================================");
            getCouponsByMaxPrice(50);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region get customer's details:
        try {
            System.out.println("=====================================================================");
            System.out.println("Customer: get customer's details via rest template:");
            System.out.println("=====================================================================");
            getCustomerDetails();
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region purchase coupon:
        try {
            System.out.println("=====================================================================");
            System.out.println("Customer: purchase coupon via rest template:");
            System.out.println("=====================================================================");
            purchaseCoupon(12);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion
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
        System.out.println("the token is: \n" + token);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void getAllCoupons() throws Exception {

        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_ALL_COUPONS_URI,
                HttpMethod.GET, getHttpEntity(null), Coupon[].class);
        checkResponse(response);
        List<Coupon> coupons = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(coupons);
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
        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_COUPONS_BY_CATEGORY_URI,
                HttpMethod.GET, getHttpEntity(null), Coupon[].class, params);

        checkResponse(response);

        List<Coupon> coupons = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(coupons);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void getCouponsByMaxPrice(double price) throws Exception {

        Map<String, Double> params = new HashMap<>();
        params.put("maxPrice", price);
        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_COUPONS_BY_MAX_PRICE_URI,
                HttpMethod.GET, getHttpEntity(null), Coupon[].class, params);

        checkResponse(response);

        List<Coupon> coupons = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(coupons);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void purchaseCoupon(long id) throws Exception {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<?> response = restTemplate.exchange(PURCHASE_COUPON_URI,
                HttpMethod.POST, getHttpEntity(null), Set.class, params);
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("purchase coupon via rest template was successful. ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void getCustomerDetails() throws Exception {

        ResponseEntity<Customer> response = restTemplate.exchange(GET_CUSTOMER_DETAILS_URI,
                HttpMethod.GET, getHttpEntity(null), Customer.class, new HashMap<>());

        checkResponse(response);
        Customer customer = response.getBody();
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(customer);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }
}
