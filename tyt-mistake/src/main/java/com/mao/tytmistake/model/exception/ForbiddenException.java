package com.mao.tytmistake.model.exception;

import com.mao.tytmistake.model.error.AuthError;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(AuthError.FORBIDDEN);
    }
}
