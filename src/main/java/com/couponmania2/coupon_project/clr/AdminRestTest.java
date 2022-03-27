package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Company;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.exceptions.*;
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
import java.util.stream.Collectors;

@Component
@Order(2)
@RequiredArgsConstructor
public class AdminRestTest implements CommandLineRunner {


    private final RestTemplate restTemplate;

    private final String LOGIN_URI = "http://localhost:8080/admin/login?clientType={clientType}&userName={userName}&userPass={userPass}";
    private final String ADD_COMPANY_URI = "http://localhost:8080/admin/addCompany";
    private final String DELETE_COMPANY_URI = "http://localhost:8080/admin/deleteCompany/{id}";
    private final String UPDATE_COMPANY_URI = "http://localhost:8080/admin/updateCompany?id={id}";
    private final String GET_ALL_COMPANIES_URI = "http://localhost:8080/admin/getAllCompanies";
    private final String GET_ONE_COMPANY_URI = "http://localhost:8080/admin/getOneCompany/{id}";
    private final String ADD_CUSTOMER_URI = "http://localhost:8080/admin/addCustomer";
    private final String DELETE_CUSTOMER_URI = "http://localhost:8080/admin/deleteCustomer/{id}";
    private final String UPDATE_CUSTOMER_URI = "http://localhost:8080/admin/updateCustomer?id={id}";
    private final String GET_ALL_CUSTOMER_URI = "http://localhost:8080/admin/getAllCustomers";
    private final String GET_ONE_CUSTOMER_URI = "http://localhost:8080/admin/getOneCustomer/{id}";

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

        //region rest template tests for customer
        try {
            getOneCustomer(1L);
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
            getAllCustomers();
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException("This is a restTemplate exception");
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }
//
        // data of customer to add:
        try {
            CustomerForm cFORM = new CustomerForm();
            cFORM.setFirstName("alon2");
            cFORM.setLastName("mintz2");
            cFORM.setEmail("alon222mintz222@mintz");
            cFORM.setPassword("ggggggggg");

            addCustomer(cFORM);
        } catch (Exception e) {
            try {
                throw new AppTargetExistsException(AppTargetExistsMessage.CUSTOMER_EXISTS);
            } catch (AppTargetExistsException err) {
                System.out.println(err.getMessage());
            }
        }

        //delete customer:
        try {
            deleteCustomer(4L);
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
            deleteCustomer(4L);
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }

        //update customer data:
        try {

            CustomerForm cFORM2 = new CustomerForm();
            cFORM2.setFirstName("notAlon");
            cFORM2.setLastName("notMintz");
            cFORM2.setEmail("alon222mintz222@mintz");
            cFORM2.setPassword("ggggggggg");

            updateCustomer(cFORM2, 2L);
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException(AppTargetNotFoundMessage.CUSTOMER_NOT_FOUND);
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }
        //endregion


        //region rest template tests for company
        try {
            getOneCompany(2L);
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
            getAllCompanies();
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException("This is a restTemplate exception");
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
            CompanyForm cFORM = new CompanyForm();
            cFORM.setName("restCompany");
            cFORM.setEmail("rest@company.com");
            cFORM.setPassword("password22022");

            addCompany(cFORM);
        } catch (Exception e) {
            try {
                throw new AppTargetExistsException(AppTargetExistsMessage.COMPANY_EXISTS);
            } catch (AppTargetExistsException err) {
                System.out.println(err.getMessage());
            }
        }

        try {
            deleteCompany(4L);
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }

        try {

            CompanyForm cFORM2 = new CompanyForm();
            // cFORM2.setName("restUpdate");
            cFORM2.setEmail("rest@update.com");
            cFORM2.setPassword("updatePass");

            updateCompany(cFORM2, 2L);
        } catch (Exception e) {
            try {
                throw new AppTargetNotFoundException(AppTargetNotFoundMessage.COMPANY_NOT_FOUND);
            } catch (AppTargetNotFoundException err) {
                System.out.println(err.getMessage());
            }
        }
        //endregion
    }

    private void login() throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("userName", "admin@admin.com");
        params.put("userPass", "admin");
        params.put("clientType", ClientType.ADMIN);
        this.token = restTemplate.postForObject(LOGIN_URI, HttpMethod.POST, String.class, params);
        this.headers = new HttpHeaders();
        headers.set("Authorization", token);
        this.httpEntity = new HttpEntity<>(headers);
        System.out.println(token);
    }

    private void getOneCustomer(Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        String c = restTemplate.exchange(GET_ONE_CUSTOMER_URI,
                HttpMethod.GET, httpEntity, String.class, params).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Customer customer = objectMapper.readValue(c, Customer.class);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println(customer);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    private void getAllCustomers() throws Exception {

        Set c = restTemplate.exchange(GET_ALL_CUSTOMER_URI,
                HttpMethod.GET, httpEntity, Set.class).getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        List <Customer> customers = c.stream()
//                .map(customer-> objectMapper.readValue(customer, Customer.class))
//                .collect(Collectors.toList());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(c);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


    }

    private void addCustomer(CustomerForm customer) throws Exception {

        this.httpEntity = new HttpEntity<>(customer, headers);
        restTemplate.exchange(ADD_CUSTOMER_URI, HttpMethod.POST, httpEntity, Void.class
                , new HashMap<>()
        );
    }

    private void deleteCustomer(Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        restTemplate.exchange(DELETE_CUSTOMER_URI, HttpMethod.DELETE,
                httpEntity, Void.class, params);
    }

    private void updateCustomer(CustomerForm customerForm, Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        this.httpEntity = new HttpEntity<>(customerForm, headers);
        restTemplate.exchange(UPDATE_CUSTOMER_URI, HttpMethod.PUT,
                httpEntity, Void.class, params);

    }

    private void getOneCompany(Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
//        String c = restTemplate.exchange(GET_ONE_COMPANY_URI,
//                HttpMethod.GET, httpEntity, String.class, params).getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        Company company = objectMapper.readValue(c, Company.class);

        Optional<Company> c = restTemplate.exchange(GET_ONE_COMPANY_URI,
                HttpMethod.GET, httpEntity, Optional.class, params).getBody();

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(c.get());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    private void getAllCompanies() throws Exception {

        Set<Company> c = restTemplate.exchange(GET_ALL_COMPANIES_URI,
                HttpMethod.GET, httpEntity, Set.class).getBody();
        System.out.println(c);
    }

    private void addCompany(CompanyForm companyForm) throws Exception {

        this.httpEntity = new HttpEntity<>(companyForm, headers);
        restTemplate.exchange(ADD_COMPANY_URI, HttpMethod.POST, httpEntity, Void.class
                , new HashMap<>()
        );
    }

    private void deleteCompany(Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        restTemplate.exchange(DELETE_COMPANY_URI, HttpMethod.DELETE,
                httpEntity, Void.class, params);
    }

    private void updateCompany(CompanyForm companyForm, Long id) throws Exception {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        this.httpEntity = new HttpEntity<>(companyForm, headers);
        restTemplate.exchange(UPDATE_COMPANY_URI, HttpMethod.PUT,
                httpEntity, Void.class, params);

    }
}
