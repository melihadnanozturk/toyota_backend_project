package com.mao.tytconduct.model.exception.auth;

import com.mao.tytconduct.model.error.AuthError;
import com.mao.tytconduct.model.exception.BaseException;

import java.io.Serial;

public class InvalidLoginRequestException extends BaseException {

    @Serial
    private static final long serialVersionUID = -6017345433456209919L;

    public InvalidLoginRequestException() {
        super(AuthError.INVALID_USER_INFO);
    }
}
