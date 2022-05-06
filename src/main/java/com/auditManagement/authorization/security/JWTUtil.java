package com.auditManagement.authorization.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    public Map<String,Object> generateToken(String username) throws IllegalArgumentException, JWTCreationException {
    	Date expirationDate = new Date(System.currentTimeMillis() + 1800000);
    	Map<String,Object> map = new HashMap<>();
    	map.put("expiresAt", expirationDate);
    	map.put("accessToken", JWT.create()
                .withSubject("User Details")
                .withExpiresAt(expirationDate)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secret)));
        return map;
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

}
