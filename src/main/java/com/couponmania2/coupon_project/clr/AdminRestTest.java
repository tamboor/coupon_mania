package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.controller.ClientController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(2)
@RequiredArgsConstructor
public class AdminRestTest implements CommandLineRunner {




    private final RestTemplate restTemplate;

     private final String LOGIN_URL = "http://localhost:8080/admin/login?clientType={clientType}&userName={userName}&userPass={userPass}";
     private final String ADD_COMPANY_URL = "http://localhost:8080/admin/addCompany";
    private final String DELETE_COMPANY_URL = "http://localhost:8080/admin/deleteCompany/{id}";
    private final String UPDATE_COMPANY_URL ="http://localhost:8080/admin/updateCompany?id={id}";
    private final String GET_ALL_COMPANIES_URL="http://localhost:8080/admin/getAllCompanies";
    private final String GET_ONE_COMPANY_URL = "http://localhost:8080/admin/getOneCompany/{id}";
    private final String ADD_CUSTOMER_URL="http://localhost:8080/admin/addCustomer";
    private final String DELETE_CUSTOMER_URL="http://localhost:8080/admin/deleteCustomer/{id}";
    private final String UPDATE_CUSTOMER_URL ="http://localhost:8080/admin/updateCustomer?id={id}";
    private final String GET_ALL_CUSTOMER_URL="http://localhost:8080/admin/getAllCustomers";
    private final String GET_ONE_CUSTOMER_URL= "http://localhost:8080/admin/getOneCustomer/{id}";





    @Override
    public void run(String... args) throws Exception {
        Map<String , Object> params = new HashMap<>();
        params.put("userName" , "admin@admin.com");
        params.put("userPass" , "admin");
        params.put("clientType" , ClientType.ADMIN);

//        ResponseEntity<?> token = restTemplate.postForEntity(LOGIN_URL , null , String.class , params);
        String token = restTemplate.postForObject(LOGIN_URL , null , String.class , params);
//        Map<String , String>
//        HttpEntity headerEntity = new HttpEntity(new HttpHeaders().set("Authorization" , token))
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" , token);
        HttpEntity httpEntity = new HttpEntity(headers);
//        System.out.println(token);

//        System.out.println(
//                restTemplate.exchange()
//        );

        getOneCustomer(1L , httpEntity);
    }
    private void getOneCustomer(Long id , HttpEntity httpEntity){
       //TODO: CHANGE URLS TO URI'S
        Map<String , Long> params = new HashMap<>();
        params.put("id" , id);

        System.out.println(
                restTemplate.exchange(GET_ONE_CUSTOMER_URL ,
                        HttpMethod.GET,httpEntity , String.class , params).getBody()
        );
    }
}
