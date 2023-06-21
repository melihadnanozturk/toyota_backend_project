package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.AuthError;

/**
 * This exception is thrown when the Client does not have the valid information
 */
public class InvalidLoginRequestException extends BaseException {

    public InvalidLoginRequestException() {
        super(AuthError.INVALID_USER_INFO);
    }
}
