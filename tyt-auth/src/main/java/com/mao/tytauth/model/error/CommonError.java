package com.mao.tytauth.model.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements Error {

    PAST_DUE_TIME(2000, "Token has expired "),
    NOT_VALID_USER(2001, "User is not valid, give correct user %s"),
    FORBIDDEN(2002, "User not have valid role, %s");

    private final Integer code;
    private final String message;
}
