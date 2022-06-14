package com.couponmania2.coupon_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CouponProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponProjectApplication.class, args);
    }
}
