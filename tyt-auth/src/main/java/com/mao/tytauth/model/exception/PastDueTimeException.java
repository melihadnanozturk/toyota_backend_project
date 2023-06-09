package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.CommonError;

public class PastDueTimeException extends BaseException {
    public PastDueTimeException() {
        super(CommonError.PAST_DUE_TIME);
    }
}
