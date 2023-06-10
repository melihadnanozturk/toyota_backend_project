package com.mao.tytauth.service;

import com.mao.tytauth.model.Role;

public interface TokenService {

    String createToken(String userName, String password);

    Boolean authentication(String token, String name);

    Boolean authorization(String userName,
                          String token,
                          Role role);
}
