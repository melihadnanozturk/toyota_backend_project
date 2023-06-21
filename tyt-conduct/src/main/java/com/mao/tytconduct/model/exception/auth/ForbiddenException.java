package com.mao.tytconduct.model.exception.auth;

import com.mao.tytconduct.model.error.AuthError;
import com.mao.tytconduct.model.exception.BaseException;

/**
 * This exception is thrown when the Client does not have the required permission
 */
public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(AuthError.FORBIDDEN);
    }
}
