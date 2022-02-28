package com.couponmania2.coupon_project.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.couponmania2.coupon_project.auth.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            //TODO: create custom userdetails
            UserDetails userDetails = new ObjectMapper().readValue(request.getInputStream() , UserDetails.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUserName(),
                    userDetails.getUserPass()
            );
            //TODO: chaeck if this works VVV
            authenticationManager.authenticate(authentication);
            //Authentication returnAuth = authenticationManager.authenticate(authentication);
            return  authentication;
        }
        catch (IOException e) {
            //TODO: change from printstacktrace
            e.printStackTrace();
            //TODO: read about throw new runtime exception
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //TODO: create jwt key
        try{
            //TODO: change to application properties
            Algorithm algorithmHS = Algorithm.HMAC256("cupomaniac".getBytes());

            String token = JWT.create()
                    .withSubject(authResult.getName())
                    .withIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(60*30)))
                    .withClaim("authorities" ,
                            authResult.getAuthorities()
                                    .stream().map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                                    .collect(Collectors.toList()))
                    .sign(algorithmHS);
            //TODO: read about refresh token
            response.addHeader("Authorization","Bearer "+token);
        }catch (JWTCreationException err) {
            //TODO: handle exception
            err.printStackTrace();
        }

    }
}
