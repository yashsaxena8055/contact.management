package com.example.contact.management.security;


import com.example.contact.management.security.service.UserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtilityService {
    @Value("${jwt.token.validity}")
    public long tokenValidity;
    @Value("${jwt.token.secret.key}")
    private String secretKey;


    public String getUsernameFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
       return claims.getSubject();
       // return extractClaims(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
       return claims.getExpiration();
        //return extractClaims(token,Claims::getExpiration);
    }

//    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        claims.getExpiration();
//        return claimsResolver.apply(claims);
//    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetail userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
