package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;

public interface LoginService {

    UserResponse userIsValid(UserRequest userRequest) throws Exception;
}
