package com.mao.tytmistake.model.exception.auth;

import com.mao.tytmistake.model.error.AuthError;
import com.mao.tytmistake.model.exception.BaseException;

/**
 * This exception is thrown when the Client does not have the required permission
 */
public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(AuthError.FORBIDDEN);
    }
}
