package com.mao.tytmistake.model.utility;

import org.springframework.http.HttpHeaders;

import java.util.Objects;

/**
 * Utility class for creating headers
 */
public class HeaderUtility {

    private static final String USER_NAME = "userName";
    private static final String AUTHORIZATION = "Authorization";

    private HeaderUtility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates a new HttpHeaders object with the specified userName and token.
     *
     * @param headers HttpHeaders
     * @return HttpHeaders that a new object with the userName and token headers set
     */
    public static HttpHeaders createHeader(HttpHeaders headers) {
        String userName = Objects.requireNonNull(headers.get(USER_NAME)).get(0);
        String token = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);

        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set(USER_NAME, userName);
        newHeaders.set(AUTHORIZATION, token);

        return newHeaders;
    }

    /**
     * Retrieves userName from headers
     *
     * @param headers HttpHeaders
     * @return String User name from headers
     */
    public static String getUser(HttpHeaders headers) {
        return Objects.requireNonNull(headers.get(USER_NAME)).get(0);
    }
}
