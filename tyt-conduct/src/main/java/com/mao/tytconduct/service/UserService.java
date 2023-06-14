package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;
import org.springframework.http.HttpHeaders;

public interface UserService {

    UserResponse addNewUser(HttpHeaders headers, UserRequest request);

    UserResponse updateUser(HttpHeaders headers, Long id, UserRequest request);

    Long removeUser(HttpHeaders headers, Long id);

}
