package com.mao.tytauth.service.impl;


import com.mao.tytauth.client.ConductApiClient;
import com.mao.tytauth.controller.response.UserResponse;
import com.mao.tytauth.model.Role;
import com.mao.tytauth.model.exception.ForbiddenException;
import com.mao.tytauth.model.exception.NotValidTokenForUserException;
import com.mao.tytauth.model.utility.HeaderUtility;
import com.mao.tytauth.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final ConductApiClient conductApiClient;


    private static final String SECRET_KEY = "maoCom";
    private static final long JWT_EXPIRATION = 900000;

    private static final String USER_NAME = "userName";
    private static final String TOKEN = "token";

    private final Logger logger = LogManager.getLogger(TokenServiceImpl.class);

    @Override
    public String createToken(HttpHeaders headers) {

        UserResponse userResponse = getUser(headers);

        return Jwts.builder()
                .setSubject(userResponse.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("Role", userResponse.getRoles())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @SneakyThrows
    @Override
    public Boolean authentication(HttpHeaders headers) {
        Map<String, String> info = HeaderUtility.getHeaderInfo(headers);

        String jwt = info.get(TOKEN).substring(7);
        String userName = info.get(USER_NAME);

        this.checkUserName(userName, jwt);

        return true;
    }

    @Override
    @SneakyThrows
    public Boolean authorization(HttpHeaders headers, Role role) {
        Map<String, String> info = HeaderUtility.getHeaderInfo(headers);

        String jwt = info.get(TOKEN);
        String userName = info.get(USER_NAME);

        String token = jwt.substring(7);

        this.checkUserName(userName, token);
        this.checkRoles(role, token);

        return true;
    }

    @SneakyThrows
    private Key getKey() {
        byte[] keyBytes = generateKey(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private byte[] generateKey(String secretkey) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(secretkey.getBytes(StandardCharsets.UTF_8));
    }

    private UserResponse getUser(HttpHeaders headers) {
        HttpHeaders clientHeader = HeaderUtility.createHeader(headers);
        String userName = Objects.requireNonNull(clientHeader.get(USER_NAME)).get(0);

        logger.atInfo().log("{} be directed Authorization", userName);
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
}
