package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;
import org.springframework.http.HttpHeaders;

/**
 * Service interface for user operations.
 */
public interface UserService {

    /**
     * This method create new user when client is valid.
     *
     * @param headers Username, Password
     * @param request Contains new user information
     */
    UserResponse addNewUser(HttpHeaders headers, UserRequest request);

    /**
     * This method update exists user when client is valid.
     *
     * @param headers Username, Password
     * @param request Contains user new information
     */
    UserResponse updateUser(HttpHeaders headers, Long id, UserRequest request);

    /**
     * This method remove exists user when client is valid.
     *
     * @param headers Username, Password
     * @param id      User that will remove id
     */
    Long removeUser(HttpHeaders headers, Long id);

}
