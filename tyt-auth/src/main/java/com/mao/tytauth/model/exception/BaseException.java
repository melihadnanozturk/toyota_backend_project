package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.Error;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final Error error;
    private final String message;

    public BaseException(Error error) {
        this.error = error;
        this.message = String.format(error.getMessage());
    }

    public BaseException(Error error, String field) {
        this.error = error;
        this.message = String.format(error.getMessage(), field);
    }

}
