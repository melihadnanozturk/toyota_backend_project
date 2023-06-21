package com.mao.tytauth.service;

import com.mao.tytauth.model.Role;
import org.springframework.http.HttpHeaders;

/**
 * This interface is used to define the methods of the TokenServiceImpl
 */
public interface TokenService {

    String createToken(HttpHeaders headers);

    Boolean authentication(HttpHeaders headers);

    Boolean authorization(HttpHeaders headers, Role role);
}
