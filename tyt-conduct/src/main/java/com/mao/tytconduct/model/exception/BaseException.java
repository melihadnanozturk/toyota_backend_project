package com.mao.tytconduct.model.exception;

import com.mao.tytconduct.model.error.TytError;
import lombok.Getter;

import java.io.Serial;

@Getter
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8046581280895159903L;

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
