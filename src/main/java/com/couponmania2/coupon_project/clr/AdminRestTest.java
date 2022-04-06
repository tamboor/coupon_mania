package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.serialization.CompanyForm;
import com.couponmania2.coupon_project.serialization.CustomerForm;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@Order(2)
@RequiredArgsConstructor
public class AdminRestTest implements CommandLineRunner {


    private final RestTemplate restTemplate;

    private final String LOGIN_URI = "http://localhost:8080/admin/login";
    private final String ADD_COMPANY_URI = "http://localhost:8080/admin/addCompany";
    private final String DELETE_COMPANY_URI = "http://localhost:8080/admin/deleteCompany/{id}";
    private final String UPDATE_COMPANY_URI = "http://localhost:8080/admin/updateCompany";
    private final String GET_ALL_COMPANIES_URI = "http://localhost:8080/admin/getAllCompanies";
    private final String GET_ONE_COMPANY_URI = "http://localhost:8080/admin/getOneCompany/{id}";
    private final String GET_COMPANY_COUPONS = "http://localhost:8080/admin/getCompanyCoupons/{id}";
    private final String ADD_CUSTOMER_URI = "http://localhost:8080/admin/addCustomer";
    private final String DELETE_CUSTOMER_URI = "http://localhost:8080/admin/deleteCustomer/{id}";
    private final String UPDATE_CUSTOMER_URI = "http://localhost:8080/admin/updateCustomer";
    private final String GET_ALL_CUSTOMER_URI = "http://localhost:8080/admin/getAllCustomers";
    private final String GET_ONE_CUSTOMER_URI = "http://localhost:8080/admin/getOneCustomer/{id}";
    private final String GET_CUSTOMER_COUPONS = "http://localhost:8080/admin/getOneCustomer/{id}";


    //private HttpEntity<?> httpEntity;
    private HttpHeaders headers;
    private String token;

    @Override
    public void run(String... args) throws Exception {

        try {
            login(UserDetails.builder().userName("admin@admin.com").userPass("admin").role("admin").build());
            System.out.println("admin login was successful via rest template ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            System.out.println(AppUnauthorizedRequestMessage.NO_LOGIN.getMessage());
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.NO_LOGIN);
        }

        //region rest template tests for customer
        try {
            getOneCustomer(1L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            getAllCustomers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
////
        // data of customer to add:
        try {
            CustomerForm cFORM = new CustomerForm();
            cFORM.setFirstName("alon2");
            cFORM.setLastName("mintz2");
            cFORM.setEmail("alon222mintz222@mintz");
            cFORM.setPassword("ggggggggg");
            cFORM.setId(2L);

            addCustomer(cFORM);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//
//        //delete customer:
        try {
            deleteCustomer(4L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //update customer data:
        try {
            CustomerForm cFORM2 = new CustomerForm();
            cFORM2.setFirstName("notAlon");
            cFORM2.setLastName("notMintz");
            cFORM2.setEmail("mail2");
            cFORM2.setPassword("ggggggggg");
            cFORM2.setId(2L);

            updateCustomer(cFORM2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //get customer coupons:
        try {
            getCustomerCoupons(3L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //endregion


        //region rest template tests for company


        try {
            getOneCompany(2L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            getAllCompanies();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            CompanyForm cFORM = new CompanyForm();
            cFORM.setName("restCompany");
            cFORM.setEmail("rest@company.com");
            cFORM.setPassword("password22022");

            addCompany(cFORM);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            deleteCompany(4L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {

            CompanyForm cFORM2 = new CompanyForm();
             cFORM2.setName("company2");
            cFORM2.setEmail("rest@update.com");
            cFORM2.setPassword("updatePass");
            cFORM2.setId(2L);

            updateCompany(cFORM2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //get company coupons:
        try {
            getCompanyCoupons(3L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
//        this.token = response.getHeaders().getFirst("authorization");
//        this.headers = new HttpHeaders();
//        headers.set("Authorization", token);
        updateTokenAndHeaders(response);
        //       this.httpEntity = new HttpEntity<>(headers);
        System.out.println(token);
    }

    private void getOneCustomer(Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<Customer> response = restTemplate.exchange(GET_ONE_CUSTOMER_URI,
                HttpMethod.GET, getHttpEntity(null), Customer.class, params);
        try {
            checkResponse(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        checkResponse(response);
        Customer customer = response.getBody();
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(customer);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    private void getAllCustomers() throws Exception {

        ResponseEntity<Customer[]> response = restTemplate.exchange(GET_ALL_CUSTOMER_URI,
                HttpMethod.GET, getHttpEntity(null), Customer[].class);
        checkResponse(response);
        List<Customer> customers = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        customers.forEach(System.out::println);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


    }

    private void addCustomer(CustomerForm customerForm) throws Exception {

        ResponseEntity<?> response = restTemplate.exchange(ADD_CUSTOMER_URI,
                HttpMethod.POST, getHttpEntity(customerForm), Void.class
                , new HashMap<>());
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Customer adding via rest template was successful");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void deleteCustomer(Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<?> response = restTemplate.exchange(DELETE_CUSTOMER_URI, HttpMethod.DELETE,
                getHttpEntity(null), Void.class, params);
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Customer delete via rest template was successful");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void updateCustomer(CustomerForm customerForm) throws Exception {

        ResponseEntity<?> response = restTemplate.exchange(UPDATE_CUSTOMER_URI, HttpMethod.PUT,
                getHttpEntity(customerForm), Void.class, new HashMap<>());
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Customer update via rest template was successful");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }
//todo: find out why doesn't work
    private void getCustomerCoupons(long id) throws Exception {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_CUSTOMER_COUPONS,
                HttpMethod.GET, getHttpEntity(null), Coupon[].class, params);

        checkResponse(response);

        List<Coupon> coupons = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        coupons.forEach(System.out::println);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void getOneCompany(Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<Company> response = restTemplate.exchange(GET_ONE_COMPANY_URI,
                HttpMethod.GET, getHttpEntity(null), Company.class, params);
        try {
            checkResponse(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        checkResponse(response);
        Company company = response.getBody();
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(company);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    private void getAllCompanies() throws Exception {

        ResponseEntity<Company[]> response = restTemplate.exchange(GET_ALL_COMPANIES_URI,
                HttpMethod.GET, getHttpEntity(null), Company[].class);
        checkResponse(response);
        List<Company> companies = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        companies.forEach(System.out::println);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void addCompany(CompanyForm companyForm) throws Exception {

        ResponseEntity<?> response = restTemplate.exchange(ADD_COMPANY_URI,
                HttpMethod.POST, getHttpEntity(companyForm), Void.class
                , new HashMap<>());
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Company adding via rest template was successful");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void deleteCompany(Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<?> response = restTemplate.exchange(DELETE_COMPANY_URI, HttpMethod.DELETE,
                getHttpEntity(null), Void.class, params);
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Company delete via rest template was successful");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void updateCompany(CompanyForm companyForm) throws Exception {

        ResponseEntity<?> response = restTemplate.exchange(UPDATE_COMPANY_URI, HttpMethod.PUT,
                getHttpEntity(companyForm), Void.class, new HashMap<>());
        checkResponse(response);
        updateTokenAndHeaders(response);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Company update via rest template was successful");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    private void getCompanyCoupons(long id) throws Exception {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_COMPANY_COUPONS,
                HttpMethod.GET, getHttpEntity(null), Coupon[].class, params);

        checkResponse(response);

        List<Coupon> coupons = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

//        Map<String, Long> params = new HashMap<>();
//        params.put("id", id);
//        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_CUSTOMER_COUPONS,
//                HttpMethod.GET, getHttpEntity(null), Coupon[].class, params);
//
//        checkResponse(response);
//
//        List<Coupon> coupons = Arrays.asList(response.getBody());
//        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        coupons.forEach(System.out::println);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
