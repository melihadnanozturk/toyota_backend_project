package com.mao.tytmistake.model.exception.auth;

import com.mao.tytmistake.model.error.AuthError;
import com.mao.tytmistake.model.exception.BaseException;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(AuthError.FORBIDDEN);
    }
}
