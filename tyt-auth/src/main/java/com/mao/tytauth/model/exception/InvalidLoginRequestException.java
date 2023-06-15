package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.AuthError;

public class InvalidLoginRequestException extends BaseException {

    public InvalidLoginRequestException() {
        super(AuthError.INVALID_USER_INFO);
    }
}
