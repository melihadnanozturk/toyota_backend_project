package com.mao.tytauth.service;

import com.mao.tytauth.controller.request.ValidateRequest;

public interface TokenService {

    String createToken(String userName, String password);

    Boolean authentication(String token, String name);

    Boolean authorization(ValidateRequest request);
}
