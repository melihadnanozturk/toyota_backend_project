package com.mao.tytconduct.model.exception;

import com.mao.tytconduct.model.error.CommonError;

public class PastDueTimeException extends BaseException {
    public PastDueTimeException() {
        super(CommonError.PAST_DUE_TIME);
    }
}
