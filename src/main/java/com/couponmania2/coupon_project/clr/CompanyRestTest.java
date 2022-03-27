package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.serialization.CouponForm;
import com.couponmania2.coupon_project.utils.DateUtils;
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

@Component
@Order(3)
@RequiredArgsConstructor
public class CompanyRestTest implements CommandLineRunner {
    private final RestTemplate restTemplate;

    private final String LOGIN_URI = "http://localhost:8080/company/login?clientType={clientType}&userName={userName}&userPass={userPass}";
    private final String ADD_COUPON_URI = "http://localhost:8080/company/addCoupon";
    private final String DELETE_COUPON_URI = "http://localhost:8080/company/deleteCoupon/{id}";
    private final String GET_ALL_COUPONS_URI = "http://localhost:8080/company/getCompanyCoupons";
    private final String GET_COUPONS_BY_MAX_PRICE_URI = "http://localhost:8080/company/getCompanyCoupons/maxPrice?maxPrice={price}";
    private final String GET_COUPONS_BY_CATEGORY_URI = "http://localhost:8080/company/getCompanyCoupons/category?category={category}";
    private final String UPDATE_COUPON_URI = "http://localhost:8080/company/updateCoupon?couponId={id}";
    private final String GET_COMPANY_DETAILS_URI = "http://localhost:8080/company/getCompanyDetails";

    private HttpEntity<?> httpEntity;
    private HttpHeaders headers;
    private String token;

    @Override
    public void run(String... args) throws Exception {
        try {
            login();
        } catch (Exception e) {
            try {
                throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.NO_LOGIN);
            } catch (AppUnauthorizedRequestException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
            CouponForm couponForm= new CouponForm();
            couponForm.setAmount(10);
            couponForm.setCategory(Category.Xtreme);
            couponForm.setDescription("rest template coupon");
            couponForm.setPrice(1000);
            couponForm.setImage("img");
            couponForm.setTitle("rest test");
            couponForm.setStartDate(DateUtils.getRandomSqlStartDate());
            couponForm.setEndDate(DateUtils.getRandomSqlEndDate());
            addCoupon(couponForm);
        } catch (Exception e) {
            try {
                throw new AppTargetExistsException(AppTargetExistsMessage.COUPON_EXISTS);
            } catch (AppTargetExistsException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
           deleteCoupon(7L);
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COUPON_NOT_FOUND);
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
            CouponForm couponForm= new CouponForm();
            couponForm.setAmount(10);
            couponForm.setCategory(Category.Xtreme);
            couponForm.setDescription("updated");
            couponForm.setPrice(444);
            couponForm.setImage("img");
            couponForm.setTitle("update test");
            couponForm.setStartDate(DateUtils.getRandomSqlStartDate());
            couponForm.setEndDate(DateUtils.getRandomSqlEndDate());
            updateCoupon(couponForm,6L);
        } catch (Exception e) {
            try {
                throw new AppInvalidInputException(AppInvalidInputMessage.UNMATCHING_COUPON);
            } catch (AppInvalidInputException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
            getAllCoupons();
        } catch (Exception e) {
            try {
                throw new AppInvalidInputException("This is a rest template exception");
            }
            catch (AppInvalidInputException err){
                System.out.println(err.getMessage());
            }
        }

        try {
            getCouponsByMaxPrice(45);
        } catch (Exception e) {
            try {
                throw new AppInvalidInputException(AppInvalidInputMessage.NEGATIVE_PRICE);
            }
            catch (AppInvalidInputException err){
                System.out.println(err.getMessage());
            }
        }

        try {
            getCouponsByCategory(Category.Xtreme);
        } catch (Exception e) {
            try {
                throw new AppInvalidInputException("This is a rest template exception");
            }
            catch (AppInvalidInputException err){
                System.out.println(err.getMessage());
            }
        }

        try {
            getCompanyDetails();
        } catch (Exception e) {
            try {
                throw new AppInvalidInputException("This is a rest template exception");
            }
            catch (AppInvalidInputException err){
                System.out.println(err.getMessage());
            }
        }

    }

    private void login() throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("userName", "email3");
        params.put("userPass", "password3");
        params.put("clientType", ClientType.COMPANY);
        this.token = restTemplate.postForObject(LOGIN_URI, HttpMethod.POST, String.class, params);
        this.headers = new HttpHeaders();
        headers.set("Authorization", token);
        this.httpEntity = new HttpEntity<>(headers);
        System.out.println(token);
    }

    private void addCoupon(CouponForm couponForm) throws Exception {
        this.httpEntity = new HttpEntity<>(couponForm, headers);
        restTemplate.exchange(ADD_COUPON_URI, HttpMethod.POST, httpEntity, Void.class
                , new HashMap<>()
        );
    }

    private void deleteCoupon(Long id) throws Exception {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        restTemplate.exchange(DELETE_COUPON_URI, HttpMethod.DELETE, httpEntity, Void.class, params);
    }

    private void updateCoupon(CouponForm couponForm, long id) throws Exception {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        this.httpEntity = new HttpEntity<>(couponForm, headers);
        restTemplate.exchange(UPDATE_COUPON_URI, HttpMethod.PUT,
                httpEntity, Void.class, params);
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

    private void getCompanyDetails() throws Exception {

        Optional<Company> c = restTemplate.exchange(GET_COMPANY_DETAILS_URI,
                HttpMethod.GET, httpEntity, Optional.class, new HashMap<>()).getBody();

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(c.get());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }
}
