package com.mao.tytauth.service;

import com.mao.tytauth.controller.request.UserRequest;
import com.mao.tytauth.controller.request.ValidateRequest;

public interface TokenService {

    String createToken(UserRequest request);

    Boolean authentication(String token, String name);

    Boolean authorization(ValidateRequest request);
}
