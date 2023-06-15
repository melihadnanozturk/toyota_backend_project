package com.mao.tytconduct.model.exception.auth;

import com.mao.tytconduct.model.error.AuthError;
import com.mao.tytconduct.model.exception.BaseException;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(AuthError.FORBIDDEN);
    }
}
