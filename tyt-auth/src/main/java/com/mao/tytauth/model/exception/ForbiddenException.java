package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.AuthError;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(AuthError.FORBIDDEN);
    }
}
