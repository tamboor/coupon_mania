package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.serialization.CouponForm;
import com.couponmania2.coupon_project.utils.DateUtils;
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
@Order(3)
@RequiredArgsConstructor

public class CompanyRestTest implements CommandLineRunner {
    private final RestTemplate restTemplate;

    private final String LOGIN_URI = "http://localhost:8080/company/login";
    private final String ADD_COUPON_URI = "http://localhost:8080/company/addCoupon";
    private final String DELETE_COUPON_URI = "http://localhost:8080/company/deleteCoupon/{id}";
    private final String GET_ALL_COUPONS_URI = "http://localhost:8080/company/getCompanyCoupons";
    private final String GET_COUPONS_BY_MAX_PRICE_URI = "http://localhost:8080/company/couponsMaxPrice/{maxPrice}";
    private final String GET_COUPONS_BY_CATEGORY_URI = "http://localhost:8080/company/couponsCategory/{category}";
    private final String UPDATE_COUPON_URI = "http://localhost:8080/company/updateCoupon";
    private final String GET_COMPANY_DETAILS_URI = "http://localhost:8080/company/getCompanyDetails";


    private HttpHeaders headers;
    private String token;

    @Override
    public void run(String... args) throws Exception {

        //region company login:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: Login via rest template:");
            System.out.println("=====================================================================");
            login(UserDetails.builder().userPass("password3").userName("email3").role("company").build());

        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region company add coupon:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: add coupon via rest template:");
            System.out.println("=====================================================================");
            CouponForm couponForm = new CouponForm();
            couponForm.setAmount(10);
            couponForm.setCategory(Category.xtreme);
            couponForm.setDescription("rest template coupon");
            couponForm.setPrice(1000);
            couponForm.setImage("img");
            couponForm.setTitle("rest test");
            couponForm.setStartDate(DateUtils.getRandomSqlStartDate());
            couponForm.setEndDate(DateUtils.getRandomSqlEndDate());
            addCoupon(couponForm);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region company delete coupon:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: delete coupon via rest template:");
            System.out.println("=====================================================================");
            deleteCoupon(7L);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region company update coupon:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: update coupon via rest template:");
            System.out.println("=====================================================================");
            CouponForm couponForm = new CouponForm();
            couponForm.setId(6);
            couponForm.setAmount(10);
            couponForm.setCategory(Category.xtreme);
            couponForm.setDescription("updated");
            couponForm.setPrice(444);
            couponForm.setImage("img");
            couponForm.setTitle("update test");
            couponForm.setStartDate(DateUtils.getRandomSqlStartDate());
            couponForm.setEndDate(DateUtils.getRandomSqlEndDate());
            updateCoupon(couponForm, 6L);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region company get all company's coupons:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: get all company's coupons via rest template:");
            System.out.println("=====================================================================");
            getAllCoupons();
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region company get all company's coupons by max price:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: get all company's coupons by max price via rest template:");
            System.out.println("=====================================================================");
            getCouponsByMaxPrice(45);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region company get all company's coupons by category:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: get all company's coupons by category via rest template:");
            System.out.println("=====================================================================");
            getCouponsByCategory(Category.xtreme);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region company get company's details:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: get company's details via rest template:");
            System.out.println("=====================================================================");
            getCompanyDetails();
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
                getHttpEntity(userDetails),
                Void.class);
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("company login was successful via rest template ");
        System.out.println("the token is: \n" + token);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void addCoupon(CouponForm couponForm) throws Exception {
        ResponseEntity<?> response = restTemplate.exchange(ADD_COUPON_URI,
                HttpMethod.POST, getHttpEntity(couponForm), Void.class
                , new HashMap<>());
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("coupon adding via rest template was successful");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void deleteCoupon(Long id) throws Exception {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<?> response = restTemplate.exchange(DELETE_COUPON_URI, HttpMethod.DELETE,
                getHttpEntity(null), Void.class, params);
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("coupon delete via rest template was successful");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void updateCoupon(CouponForm couponForm, long id) throws Exception {
        ResponseEntity<?> response = restTemplate.exchange(UPDATE_COUPON_URI, HttpMethod.PUT,
                getHttpEntity(couponForm), Void.class, new HashMap<>());
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("coupon update via rest template was successful");
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

    private void getCompanyDetails() throws Exception {

        ResponseEntity<Customer> response = restTemplate.exchange(GET_COMPANY_DETAILS_URI,
                HttpMethod.GET, getHttpEntity(null), Customer.class, new HashMap<>());

        checkResponse(response);
        Customer customer = response.getBody();
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(customer);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }
}
