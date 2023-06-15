package com.mao.tytauth.service.impl;


import com.mao.tytauth.client.ConductApiClient;
import com.mao.tytauth.client.HeaderUtility;
import com.mao.tytauth.controller.response.UserResponse;
import com.mao.tytauth.model.Role;
import com.mao.tytauth.model.exception.ForbiddenException;
import com.mao.tytauth.model.exception.NotValidTokenForUserException;
import com.mao.tytauth.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final ConductApiClient conductApiClient;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private static final String USER_NAME = "userName";
    private static final String TOKEN = "token";
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public String createToken(HttpHeaders headers) {
        UserResponse userResponse = getUser(headers);

        return Jwts.builder()
                .setSubject(userResponse.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("Role", userResponse.getRoles())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @SneakyThrows
    @Override
    public Boolean authentication(HttpHeaders headers) {
        Map<String, String> info = getHeaderInfo(headers);

        String jwt = info.get(TOKEN).substring(7);
        String userName = info.get(USER_NAME);

        this.checkUserName(userName, jwt);

        return true;
    }

    @Override
    @SneakyThrows
    public Boolean authorization(HttpHeaders headers, Role role) {
        Map<String, String> info = getHeaderInfo(headers);

        String jwt = info.get(TOKEN);
        String userName = info.get(USER_NAME);

        String token = jwt.substring(7);

        this.checkUserName(userName, token);
        this.checkRoles(role, token);

        return true;
    }

    @SneakyThrows
    public Key getKey() {
        byte[] keyBytes = generateKey(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private byte[] generateKey(String secretkey) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(secretkey.getBytes(StandardCharsets.UTF_8));
    }

    private UserResponse getUser(HttpHeaders headers) {
        HttpHeaders clientHeader = HeaderUtility.createHeader(headers);

        return conductApiClient.userIsValid(clientHeader).getResponse();
    }

    private String getUserName(String token) {
        final Claims claims = this.extractAllClaims(token);
        return claims.getSubject();
    }

    private void checkRoles(Role role, String token) {
        Claims claims = extractAllClaims(token);
        List roles = claims.get("Role", List.class);
        if (!roles.contains(role.getName())) {
            throw new ForbiddenException();
        }
    }

    private void checkUserName(String userName, String token) {
        String tokenUser = getUserName(token);

        if (!userName.equals(tokenUser)) {
            throw new NotValidTokenForUserException(userName);
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Map<String, String> getHeaderInfo(HttpHeaders headers) {
        Map<String, String> infos = new HashMap<>();

        String userName = Objects.requireNonNull(headers.get(USER_NAME)).get(0);
        String token = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);

        infos.put(USER_NAME, userName);
        infos.put(TOKEN, token);

        return infos;
    }
}
