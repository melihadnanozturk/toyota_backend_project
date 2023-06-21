package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.response.UserResponse;
import org.springframework.http.HttpHeaders;

/**
 * Service interface for login functionality.
 */
public interface LoginService {

    /**
     * This method checks if the given information is valid.
     *
     * @param headers - Username, Password
     */
    UserResponse isUserValid(HttpHeaders headers);
}
