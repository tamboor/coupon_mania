package com.couponmania2.coupon_project.clr;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Order(3)
@RequiredArgsConstructor
public class CompanyRestTest {
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
}
