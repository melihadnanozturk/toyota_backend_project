package com.mao.tytauth.client;

import org.springframework.http.HttpHeaders;

import java.util.Objects;

public class HeaderUtility {

    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";

    private HeaderUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static HttpHeaders createHeader(HttpHeaders headers) {
        String userName = Objects.requireNonNull(headers.get(USER_NAME)).get(0);
        String token = Objects.requireNonNull(headers.get(PASSWORD)).get(0);

        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set(USER_NAME, userName);
        newHeaders.set(PASSWORD, token);

        return newHeaders;
    }
}
