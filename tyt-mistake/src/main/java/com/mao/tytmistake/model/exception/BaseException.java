package com.mao.tytmistake.model.exception;

import com.mao.tytmistake.model.error.Error;
import lombok.Getter;

import java.io.Serial;

@Getter
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8046581280895159903L;

    private final Error error;
    private final String message;

    public BaseException(Error error, String field) {
        this.error = error;
        this.message = String.format(error.getMessage(), field);
    }

}