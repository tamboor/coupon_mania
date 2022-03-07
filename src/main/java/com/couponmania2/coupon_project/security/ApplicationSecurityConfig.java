//package com.couponmania2.coupon_project.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final BCryptPasswordEncoder PASSWORD_ENCODER;
//    private final UserDetailsService USER_DETAILS_SERVICE;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(USER_DETAILS_SERVICE).passwordEncoder(PASSWORD_ENCODER);
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
//                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class);
//    }

//}
