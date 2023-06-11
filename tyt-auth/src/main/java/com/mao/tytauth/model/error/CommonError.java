package com.mao.tytauth.model.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements Error {

    PAST_DUE_TIME(2000, "TOKEN HAS EXPIRED"),
    NOT_VALID_TOKEN(2001, "NOT VALID TOKEN FOR USER, %s"),
    FORBIDDEN(2002, "NOT HAVE VALID ROLE, %s");

    private final Integer code;
    private final String message;
}
