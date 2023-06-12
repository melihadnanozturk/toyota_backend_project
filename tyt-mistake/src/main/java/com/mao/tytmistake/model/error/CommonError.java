package com.mao.tytmistake.model.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements Error {

    NOT_FOUND(1000, "Record not found with %s id"),
    ALREADY_EXIST(1001, "Record already exists by %s"),
    PAST_DUE_TIME(2000, "Token has expired"),
    NOT_VALID_TOKEN(2001, "The token is not compatible with the user. Permission denied, %s"),
    FORBIDDEN(2002, "Access Denied. You do not have permission to access the requested resource");;

    private final Integer code;
    private final String message;
}
