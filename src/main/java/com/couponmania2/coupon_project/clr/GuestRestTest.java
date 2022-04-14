package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.auth.ClientType;
import com.couponmania2.coupon_project.beans.Coupon;
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

import java.util.Arrays;
import java.util.List;


@Component
@Order(5)
@RequiredArgsConstructor
public class GuestRestTest implements CommandLineRunner {
    private final RestTemplate restTemplate;

    private final String GET_ALL_COUPONS_URI = "http://localhost:8080/guest/getAllCoupons";

    private HttpHeaders headers;
    private String token;

    @Override
    public void run(String... args) throws Exception {

        //region guest get all system's coupons:
        try {
            System.out.println("=====================================================================");
            System.out.println("Guest: get all coupons via rest template:");
            System.out.println("=====================================================================");
            getAllCoupons();
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

}
