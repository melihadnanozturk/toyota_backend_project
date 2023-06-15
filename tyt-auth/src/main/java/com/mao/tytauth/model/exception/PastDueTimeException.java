package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.AuthError;

public class PastDueTimeException extends BaseException {
    public PastDueTimeException() {
        super(AuthError.PAST_DUE_TIME);
    }
}
