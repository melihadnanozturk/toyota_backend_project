package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.response.UserResponse;

public interface LoginService {

    UserResponse userIsValid(String userName, String password);
}
