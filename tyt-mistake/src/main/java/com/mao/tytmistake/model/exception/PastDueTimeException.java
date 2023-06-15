package com.mao.tytmistake.model.exception;

import com.mao.tytmistake.model.error.AuthError;

public class PastDueTimeException extends BaseException {

    public PastDueTimeException() {
        super(AuthError.PAST_DUE_TIME);
    }
}
