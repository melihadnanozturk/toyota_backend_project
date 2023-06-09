package com.mao.tytauth.model.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements Error {

    PAST_DUE_TIME(2000, "Token has expired "),
    NOT_VALID_USER(1001, "User is not valid, give correct user %s");

    private final Integer code;
    private final String message;
}
