package com.mao.tytauth.service.impl;


import com.mao.tytauth.client.ConductApiClient;
import com.mao.tytauth.controller.request.UserRequest;
import com.mao.tytauth.controller.request.ValidateRequest;
import com.mao.tytauth.controller.response.UserResponse;
import com.mao.tytauth.model.Role;
import com.mao.tytauth.model.exception.ForbiddenException;
import com.mao.tytauth.model.exception.NotValidUserException;
import com.mao.tytauth.model.exception.PastDueTimeException;
import com.mao.tytauth.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final ConductApiClient conductApiClient;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Override
    public String createToken(String userName, String password) {
        UserResponse userResponse = getUser(userName, password);

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
    public Boolean authentication(String token, String userName) {
        String jwt = token.substring(7);

        checkExpiration(jwt);
        checkUserName(userName, jwt);

        return true;
    }

    @Override
    @SneakyThrows
    public Boolean authorization(ValidateRequest request) {
        String jwt = request.getToken().substring(7);

        checkExpiration(jwt);
        checkUserName(request.getUser(), jwt);
        checkRoles(request.getRole(), jwt);

        return true;
    }

    @SneakyThrows
    public Key getKey() {
        byte[] keyBytes = generateKey(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private UserResponse getUser(String userName, String password) {
        UserRequest request = UserRequest.builder()
                .name(userName)
                .password(password)
                .build();

        return conductApiClient.userIsValid(request).getResponse();
    }

    private String getUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private void checkRoles(Role role, String token) throws Exception {
        Claims claims = extractAllClaims(token);
        List roles = claims.get("Role", List.class);
        if (!roles.contains(role)) {
            throw new ForbiddenException(role.toString());
        }
    }

    private void checkExpiration(String token) {
        Date date = extractClaim(token, Claims::getExpiration);

        if (!date.after(Date.from(Instant.now()))) {
            throw new PastDueTimeException();
        }
    }

    private void checkRole(Role role, String token) {

    }

    private void checkUserName(String userName, String token) {
        String userFToken = getUserName(token);

        if (!userName.equals(userFToken)) {
            throw new NotValidUserException(userName);
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

    private byte[] generateKey(String secretkey) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(secretkey.getBytes(StandardCharsets.UTF_8));
    }
}