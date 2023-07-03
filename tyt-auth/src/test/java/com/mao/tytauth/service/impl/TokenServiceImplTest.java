package com.mao.tytauth.service.impl;

import com.mao.tytauth.BaseUnitTest;
import com.mao.tytauth.client.ConductApiClient;
import com.mao.tytauth.controller.response.BaseResponse;
import com.mao.tytauth.controller.response.UserResponse;
import com.mao.tytauth.controller.response.UserResponseBuilder;
import com.mao.tytauth.model.Role;
import com.mao.tytauth.model.exception.ForbiddenException;
import com.mao.tytauth.model.exception.NotValidTokenForUserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TokenServiceImplTest extends BaseUnitTest {

    @Mock
    private ConductApiClient conductApiClient;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    void createToken_happyPath() throws NoSuchAlgorithmException {
        String testName = "testName";
        String testPassword = "testPass";
        HttpHeaders testHeaders = this.createHeader(testName, testPassword);
        UserResponse testResponse = new UserResponseBuilder().build();

        when(conductApiClient.userIsValid(any(HttpHeaders.class)))
                .thenReturn(BaseResponse.isSuccess(testResponse));

        String response = tokenService.createToken(testHeaders);
        Claims testClaims = Jwts.parserBuilder().setSigningKey(createTestKey()).build().parseClaimsJws(response).getBody();

        Assertions.assertEquals(testClaims.getSubject(), testResponse.getName());
        testResponse.getRoles().forEach(role ->
                Assertions.assertTrue(testClaims.get("Role", List.class).contains(role.getName())));
        verify(conductApiClient, times(1)).userIsValid(any(HttpHeaders.class));
    }

    @Test
    void authentication_invalidUserNameForToken_throwNotValidTokenForUserException() {
        String testName = "testName";
        String testPassword = "testPass";
        HttpHeaders testHeadersForToken = this.createHeader(testName, testPassword);
        UserResponse testResponse = new UserResponseBuilder().build();

        when(conductApiClient.userIsValid(any(HttpHeaders.class)))
                .thenReturn(BaseResponse.isSuccess(testResponse));

        String testToken = tokenService.createToken(testHeadersForToken);
        HttpHeaders testForAuthentication = this.createHeaderForToken("invalidName", testToken);

        Assertions.assertThrows(NotValidTokenForUserException.class, () -> tokenService.authentication(testForAuthentication));
    }

    @Test
    void authentication_happyPath() {
        String testName = "testName";
        String testPassword = "testPass";
        HttpHeaders testHeadersForToken = this.createHeader(testName, testPassword);
        UserResponse testResponse = new UserResponseBuilder()
                .withName(testName)
                .withPassword(testPassword)
                .build();

        when(conductApiClient.userIsValid(any(HttpHeaders.class)))
                .thenReturn(BaseResponse.isSuccess(testResponse));

        String testToken = tokenService.createToken(testHeadersForToken);
        HttpHeaders testForAuthentication = this.createHeaderForToken(testName, testToken);

        Assertions.assertDoesNotThrow(() -> tokenService.authentication(testForAuthentication));
    }

    @Test
    void authorization_notValidRole_thenThrowForbiddenException() {
        String testName = "testName";
        String testPassword = "testPass";
        HttpHeaders testHeadersForToken = this.createHeader(testName, testPassword);
        UserResponse testResponse = new UserResponseBuilder()
                .withName(testName)
                .withPassword(testPassword)
                .withRole(Role.ADMIN)
                .build();

        when(conductApiClient.userIsValid(any(HttpHeaders.class)))
                .thenReturn(BaseResponse.isSuccess(testResponse));

        String testToken = tokenService.createToken(testHeadersForToken);
        HttpHeaders testForAuthentication = this.createHeaderForToken(testName, testToken);

        Assertions.assertThrows(ForbiddenException.class, () -> tokenService.authorization(testForAuthentication, Role.OPERATOR));
    }

    @Test
    void authorization_happyPath() {
        String testName = "testName";
        String testPassword = "testPass";
        HttpHeaders testHeadersForToken = this.createHeader(testName, testPassword);
        UserResponse testResponse = new UserResponseBuilder()
                .withName(testName)
                .withPassword(testPassword)
                .withRole(Role.ADMIN)
                .build();

        when(conductApiClient.userIsValid(any(HttpHeaders.class)))
                .thenReturn(BaseResponse.isSuccess(testResponse));

        String testToken = tokenService.createToken(testHeadersForToken);
        HttpHeaders testForAuthentication = this.createHeaderForToken(testName, testToken);

        Boolean response = tokenService.authorization(testForAuthentication, Role.ADMIN);

        Assertions.assertTrue(response);
    }

    private HttpHeaders createHeader(String userName, String password) {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", userName);
        newHeaders.set("password", password);

        return newHeaders;
    }

    private HttpHeaders createHeaderForToken(String userName, String token) {
        HttpHeaders newHeaders = new HttpHeaders();
        String jwtToken = "Bearer ";
        newHeaders.set("userName", userName);
        newHeaders.set("Authorization", jwtToken + token);

        return newHeaders;
    }

    private Key createTestKey() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest("maoCom".getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}