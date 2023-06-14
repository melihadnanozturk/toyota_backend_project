package com.mao.tytauth.service;

import com.mao.tytauth.model.Role;
import org.springframework.http.HttpHeaders;

public interface TokenService {

    String createToken(String userName, String password);

    Boolean authentication(String token, String name);

    Boolean authorization(HttpHeaders headers, Role role);

    Boolean authorization(String userName,
                          String token,
                          Role role);
}
