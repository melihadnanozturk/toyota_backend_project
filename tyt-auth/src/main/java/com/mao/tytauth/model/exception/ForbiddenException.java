package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.AuthError;

/**
 * This exception is thrown when the Client does not have the required permission
 */
public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(AuthError.FORBIDDEN);
    }
}
