package com.bank.app.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("JwtUtils.java Token: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // remove bearer token
        }
        return null;
    }

    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();

    }

    public Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public boolean validateJwtToken(String jwtToken) {
        try{
            logger.debug("Validating JWT Token: {}", jwtToken);
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(jwtToken);
            return true;
        } catch (MalformedJwtException e){
            logger.error("Invalid JWT Token: {}", jwtToken);
        } catch (ExpiredJwtException e){
            logger.error("Expired JWT Token: {}", jwtToken);
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT Token: {}", jwtToken);
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", jwtToken);
        }
        return false;
    }

    public String getUsernameFromJWTToken(String jwt) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt)
                .getPayload().getSubject();
    }
}
