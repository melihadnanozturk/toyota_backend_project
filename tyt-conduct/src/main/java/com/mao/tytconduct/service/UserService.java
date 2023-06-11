package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;

public interface UserService {

    UserResponse addNewUser(String userName, String token, UserRequest request);

    UserResponse updateUser(Long id, UserRequest request);

    Long removeUser(Long id);

}
