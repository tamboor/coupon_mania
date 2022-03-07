package com.couponmania2.coupon_project.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestMessage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
@Service
public class JwtUtils {

    public String generateToken(UserDetails userDetails) {
        //TODO: create jwt key
        try {
            //TODO: change to application properties+ check if needs to be final
            Algorithm algorithmHS = Algorithm.HMAC256("alon_nir_ran_the_kings_of_the_valley".getBytes());
            String token = JWT.create()
                    .withSubject(userDetails.getUserName())
                    //todo: create dateUtils
                    .withIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(5)))
                    .withClaim("id", userDetails.getId())
                    .withClaim("role", userDetails.getClientType().getName())
//                    .withClaim("authorities" ,
//                            authResult.getAuthorities()
//                                    .stream().map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
//                                    .collect(Collectors.toList()))
                    .sign(algorithmHS);

            return token;
            //TODO: read about refresh token

        } catch (JWTCreationException err) {
            //TODO: handle exception
            err.printStackTrace();
        }
        return null;
    }

    public void validateToken(String token) throws AppUnauthorizedRequestException {
        try{
            DecodedJWT jwt = JWT.decode(token);
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("alon_nir_ran_the_kings_of_the_valley".getBytes()))
               //     .acceptExpiresAt(1)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt.getToken());
        } catch (TokenExpiredException err){
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.LOGIN_EXPIRED.getMessage());

        }
        catch (Exception err){
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.NO_LOGIN.getMessage());
        }


//        return (userMail.equals(user.getUserName()) &&
//                !isTokenExpired(token));
    }
}
