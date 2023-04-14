package com.mao.tytmistake.model.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements Error {

    NOT_FOUND(1000, "Record not found with %s id"),
    ALREADY_EXIST(1001, "Record already exists by %s");

    private final Integer code;
    private final String message;
}
