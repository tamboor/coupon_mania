package com.couponmania2.coupon_project.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtTokenVerifier extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //TODO: change path to login path
        if (request.getServletPath().equals("/api/login/")){
            filterChain.doFilter(request , response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

            //TODO: move to app properties
            String myKey = "cupomaniac";

            try {
                String token = authHeader.replace("Bearer ", "");
                DecodedJWT decodedToken = JWT.decode(token);
                String subject = decodedToken.getSubject();
                //TODO: this might not work,might need to convert to string before having granted authority collection
                List<SimpleGrantedAuthority> authorities = decodedToken.getClaim("authorities").asList(SimpleGrantedAuthority.class);
                Authentication authentication = new UsernamePasswordAuthenticationToken(subject, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (JWTDecodeException err) {
                //TODO: handle stacktrace , change to bad login in error - add exception to response header, response.senderror
                err.printStackTrace();
            }


    }
}
