package com.mao.tytconduct.model.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements TytError {

    RECORD_NOT_FOUND(1000, "Record not found by id, %s"),
    ALREADY_EXIST(1001, "Record already exists, %s");

    private final Integer code;
    private final String message;
}
