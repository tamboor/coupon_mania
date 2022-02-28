package com.couponmania2.coupon_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CouponProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponProjectApplication.class, args);
    }

    //todo: find way to move to config
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
