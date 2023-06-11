package com.mao.tytconduct.model.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements TytError {

    NOT_FOUND(1000, "Record not found with %s id"),
    ALREADY_EXIST(1001, "Record already exists by %s"),
    PAST_DUE_TIME(2000, "Token has expired "),
    NOT_VALID_USER(2001, "User is not valid, give correct user %s"),
    FORBIDDEN(2002, "User not have valid role, %s");

    private final Integer code;
    private final String message;
}
