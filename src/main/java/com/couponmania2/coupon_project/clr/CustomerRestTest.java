package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

//@Component
@Order(4)
@RequiredArgsConstructor
//todo: add get customer details method
//todo: change to updated URIs

public class CustomerRestTest implements CommandLineRunner {
    private final RestTemplate restTemplate;
    private final String LOGIN_URI = "http://localhost:8080/customer/login?clientType={clientType}&userName={userName}&userPass={userPass}";
    private final String PURCHASE_COUPON_URI = "http://localhost:8080/customer/newPurchase?couponId={id}";
    private final String GET_ALL_COUPONS_URI = "http://localhost:8080/customer/getCustomerCoupons";
    private final String GET_COUPONS_BY_MAX_PRICE_URI = "http://localhost:8080/customer/getCustomerCoupons/maxPrice?maxPrice={price}";
    private final String GET_COUPONS_BY_CATEGORY_URI = "http://localhost:8080/customer/getCustomerCoupons/category?category={category}";
    private final String GET_CUSTOMER_DETAILS_URI = "http://localhost:8080/customer/getCustomerDetails\n";

    private HttpEntity<?> httpEntity;
    private HttpHeaders headers;
    private String token;

    @Override
    public void run(String... args) throws Exception {

        login();
        purchaseCoupon(10);


    }
    private void login() throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("userName", "mail1");
        params.put("userPass", "pass");
        params.put("clientType", ClientType.customer);
        this.token = restTemplate.postForObject(LOGIN_URI, HttpMethod.POST, String.class, params);
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

    private void purchaseCoupon(long id) throws Exception{
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
       restTemplate.exchange(PURCHASE_COUPON_URI,
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
