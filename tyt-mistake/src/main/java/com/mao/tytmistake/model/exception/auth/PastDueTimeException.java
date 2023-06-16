package com.mao.tytmistake.model.exception.auth;

import com.mao.tytmistake.model.error.AuthError;
import com.mao.tytmistake.model.exception.BaseException;

public class PastDueTimeException extends BaseException {

    public PastDueTimeException() {
        super(AuthError.PAST_DUE_TIME);
    }
}
