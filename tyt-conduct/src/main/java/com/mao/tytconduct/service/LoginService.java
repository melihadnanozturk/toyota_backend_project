package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.response.UserResponse;
import org.springframework.http.HttpHeaders;

public interface LoginService {

    UserResponse isUserValid(HttpHeaders headers);
}
