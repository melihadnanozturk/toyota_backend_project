package com.mao.tytauth.service;

import com.mao.tytauth.model.User;

import java.util.Map;

public interface TokenService {

    String createToken(User user);

    String createToken(Map<String, Object> claims, User user);

    boolean isValid(String token);

    String getUserName(String token);
}
