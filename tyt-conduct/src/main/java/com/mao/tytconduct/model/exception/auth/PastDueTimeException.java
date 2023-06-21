package com.mao.tytconduct.model.exception.auth;

import com.mao.tytconduct.model.error.AuthError;
import com.mao.tytconduct.model.exception.BaseException;

/**
 * This exception is thrown when Client Token expires
 */
public class PastDueTimeException extends BaseException {
    public PastDueTimeException() {
        super(AuthError.PAST_DUE_TIME);
    }
}
