package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.TytError;
import lombok.Getter;

/**
 * This Exception Class is a base class for exceptions
 * This class extends RuntimeException
 * This class uses Lombok annotations @Getter to automatically generate getters
 */
@Getter
public class BaseException extends RuntimeException {

    private final TytError error;
    private final String message;

    public BaseException(TytError error) {
        this.error = error;
        this.message = String.format(error.getMessage());
    }

    public BaseException(TytError error, String field) {
        this.error = error;
        this.message = String.format(error.getMessage(), field);
    }

}
