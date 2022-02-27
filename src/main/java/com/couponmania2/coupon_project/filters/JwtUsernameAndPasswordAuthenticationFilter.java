package com.couponmania2.coupon_project.filters;

import com.couponmania2.coupon_project.auth.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            UserDetails userDetails = new ObjectMapper().readValue(request.getInputStream() , UserDetails.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUserName(),
                    userDetails.getUserPass()
            );
            //todo: chaeck if this works VVV
            authenticationManager.authenticate(authentication);
            //Authentication returnAuth = authenticationManager.authenticate(authentication);
            return  authentication;
        }
        catch (IOException e) {
            //todo: change from printstacktrace
            e.printStackTrace();
            //todo: read about throw new runtime exception
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        //todo: create jwt key
    }
}
