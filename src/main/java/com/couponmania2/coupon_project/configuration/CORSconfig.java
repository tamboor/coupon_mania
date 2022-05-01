package com.couponmania2.coupon_project.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSconfig {
    @Bean
    public FilterRegistrationBean corsFilter() {

        //create new cors configuration...
        CorsConfiguration config = new CorsConfiguration();
        //allow to get credentials in cors
//        config.setAllowCredentials(true);
        //allow to get from any ip/domain
        config.addAllowedOrigin("*");
        //allow to get any header
        config.addAllowedHeader("*");
        //tell which VERB is allowed
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("DELETE");
        //allow to get any route -> localhost:8080/api/coupon-> /api/coupon and implement the config
        //create new url configuration for browsers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        //return new configuration
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
