package com.mao.tytauth.model.utility;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HeaderUtility {

    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    private static final String AUTHORIZATION = "Authorization";

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

    public static Map<String, String> getHeaderInfo(HttpHeaders headers) {
        Map<String, String> infos = new HashMap<>();

        String userName = Objects.requireNonNull(headers.get(USER_NAME)).get(0);
        String token = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);

        infos.put(USER_NAME, userName);
        infos.put(TOKEN, token);

        return infos;
    }
}
