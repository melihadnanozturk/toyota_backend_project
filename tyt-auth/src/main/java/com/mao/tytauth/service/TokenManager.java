package com.mao.tytauth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenManager {

    private String jwtSecret = "DENEME";

    private int jwtExpiration = 10;

    public String createToken(String userName) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, "deneme")
                .compact();
    }

    public String getUserName(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean tokenValidate(String token) {
        if (getUserName(token) != null && isExpired(token)) {
            return true;
        }
        return false;
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

}
