package com.mao.tytmistake.model.exception;

import com.mao.tytmistake.model.error.TytError;
import lombok.Getter;

import java.io.Serial;

/**
 * Base exception class for TYT(Toyota-Backend) system.
 */
@Getter
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8046581280895159903L;

    private final TytError error;
    private final String message;

    /**
     * Constructs a new BaseException with the specified error.
     *
     * @param error Error associated with the exception
     */
    public BaseException(TytError error) {
        this.error = error;
        this.message = String.format(error.getMessage());
    }

    /**
     * Constructs a new BaseException with the specified error and field.
     *
     * @param error Error associated with the exception
     * @param field Field related to the exception
     */
    public BaseException(TytError error, String field) {
        this.error = error;
        this.message = String.format(error.getMessage(), field);
    }

}
