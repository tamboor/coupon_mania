package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.serialization.CompanyForm;
import com.couponmania2.coupon_project.serialization.CustomerForm;
import com.couponmania2.coupon_project.utils.TablePrinter;
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

//@Component
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
    private final String GET_CUSTOMER_COUPONS = "http://localhost:8080/admin/getCustomerCoupons/{id}";


    //private HttpEntity<?> httpEntity;
    private HttpHeaders headers;
    private String token;

    @Override
    public void run(String... args) throws Exception {

        //region admin login:
        try {
            System.out.println("=====================================================================");
            System.out.println("Admin: Login via rest template:");
            System.out.println("=====================================================================");
            login(UserDetails.builder().userName("admin@admin.com").userPass("admin").role("admin").build());

        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        //endregion

        //region rest template tests for customer

            //region get one customer
        try {
            System.out.println("=====================================================================");
            System.out.println("Admin: get one customer via rest template:");
            System.out.println("=====================================================================");
            getOneCustomer(1L);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region get all customers
        try {
            System.out.println("=====================================================================");
            System.out.println("Admin: get all customers via rest template:");
            System.out.println("=====================================================================");
            getAllCustomers();
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region add customer:
        try {
            System.out.println("=====================================================================");
            System.out.println("Admin: add customer via rest template:");
            System.out.println("=====================================================================");
            CustomerForm cFORM = new CustomerForm();
            cFORM.setFirstName("restCustomer");
            cFORM.setLastName("test");
            cFORM.setEmail("rest@customer");
            cFORM.setPassword("testing");
            ///cFORM.setId(8L);

            addCustomer(cFORM);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region delete customer:
        try {
            System.out.println("=====================================================================");
            System.out.println("Admin: delete customer via rest template:");
            System.out.println("=====================================================================");
            deleteCustomer(1L);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region update customer:
        try {
            System.out.println("=====================================================================");
            System.out.println("Admin: update customer via rest template:");
            System.out.println("=====================================================================");
            CustomerForm cFORM2 = new CustomerForm();
            cFORM2.setFirstName("newAlon");
            cFORM2.setLastName("newMintz");
            cFORM2.setEmail("alon@mintz");
            cFORM2.setPassword("alonpass");
            cFORM2.setId(2L);

            updateCustomer(cFORM2);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region get customer's coupons:
        try {
            System.out.println("=====================================================================");
            System.out.println("Admin: get customer's coupons via rest template:");
            System.out.println("=====================================================================");
            getCustomerCoupons(3L);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

        //endregion

        //region rest template tests for company

            //region get onr company:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: get one company via rest template:");
            System.out.println("=====================================================================");
            getOneCompany(2L);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region get all companies
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: get all companies via rest template:");
            System.out.println("=====================================================================");
            getAllCompanies();
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region add company:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: add company via rest template:");
            System.out.println("=====================================================================");
            CompanyForm cFORM = new CompanyForm();
            cFORM.setName("restCompany");
            cFORM.setEmail("rest@company.com");
            cFORM.setPassword("password22022");

            addCompany(cFORM);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region delete company
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: delete company via rest template:");
            System.out.println("=====================================================================");
            deleteCompany(1L);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region update company:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: update company via rest template:");
            System.out.println("=====================================================================");
            CompanyForm cFORM2 = new CompanyForm();
            cFORM2.setName("company2");
            cFORM2.setEmail("rest@update.com");
            cFORM2.setPassword("updatePass");
            cFORM2.setId(2L);

            updateCompany(cFORM2);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

            //region get company coupons:
        try {
            System.out.println("=====================================================================");
            System.out.println("Company: get company's coupons via rest template:");
            System.out.println("=====================================================================");
            getCompanyCoupons(3L);
        } catch (Exception e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("!!! REST TEMPLATE ERROR !!!");
            System.out.println(e.getMessage());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
            //endregion

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
        System.out.println("admin login was successful via rest template ");
        System.out.println(token);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

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
        TablePrinter.print(customer);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    private void getAllCustomers() throws Exception {

        ResponseEntity<Customer[]> response = restTemplate.exchange(GET_ALL_CUSTOMER_URI,
                HttpMethod.GET, getHttpEntity(null), Customer[].class);
        checkResponse(response);
        List<Customer> customers = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(customers);
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

    private void getCustomerCoupons(long id) throws Exception {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<Coupon[]> response = restTemplate.exchange(GET_CUSTOMER_COUPONS,
                HttpMethod.GET, getHttpEntity(null), Coupon[].class, params);

        checkResponse(response);

        List<Coupon> coupons = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(coupons);
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
        TablePrinter.print(company);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    private void getAllCompanies() throws Exception {

        ResponseEntity<Company[]> response = restTemplate.exchange(GET_ALL_COMPANIES_URI,
                HttpMethod.GET, getHttpEntity(null), Company[].class);
        checkResponse(response);
        List<Company> companies = Arrays.asList(response.getBody());
        updateTokenAndHeaders(response);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(companies);
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

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        TablePrinter.print(coupons);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
