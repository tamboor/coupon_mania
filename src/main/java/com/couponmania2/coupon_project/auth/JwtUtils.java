package com.couponmania2.coupon_project.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestException;
import com.couponmania2.coupon_project.exceptions.AppUnauthorizedRequestMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Service
public class JwtUtils {

    private final String idClaimKey = "id";
    private final String roleClaimKey = "role";
    private final String secretKey = "alon_nir_ran_the_kings_of_the_valley";

    /**
     * method that generates a token for the user to use.
     *
     * @param userDetails details of the logged-in user
     * @return the token.
     */
    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(secretKey.getBytes());
            String token = JWT.create()
                    .withSubject(userDetails.getUserName())
                    .withIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(60 * 30)))
                    .withClaim(idClaimKey, userDetails.getId())
                    .withClaim(roleClaimKey, userDetails.getRole())
                    .sign(algorithmHS);

            return token;

        } catch (JWTCreationException err) {
            //TODO: handle
        }
        return null;
    }

    /**
     * decodes the token by the algorithm
     * validates it's expiration time
     *
     * @param token the token
     * @return the user details that are encoded it the token
     * @throws AppUnauthorizedRequestException if the token has expired or if the user is un-authorized.
     */
    public UserDetails validateToken(String token) throws AppUnauthorizedRequestException {

        if (!(token.startsWith("Bearer "))) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.INVALID_TOKEN);
        }

        String extractedToken = token.substring("Bearer ".length());

        try {
            DecodedJWT jwt = JWT.decode(extractedToken);
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey.getBytes()))
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt.getToken());
            UserDetails user = new UserDetails();
            user.setUserName(decodedJWT.getSubject());
            user.setId(decodedJWT.getClaim(idClaimKey).asLong());
            user.setRole(decodedJWT.getClaim(roleClaimKey).asString());
            return user;
        } catch (TokenExpiredException err) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.LOGIN_EXPIRED);
        } catch (Exception err) {
            throw new AppUnauthorizedRequestException(AppUnauthorizedRequestMessage.INVALID_TOKEN);
        }
    }

    /**
     * Generates a header containing a bearer authentication token by UserDetails.
     *
     * @param userDetails the UserDetails stored in the token.
     * @return The header generated.
     */
    public HttpHeaders getHeaderWithToken(UserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(generateToken(userDetails));
        return headers;
    }
}
