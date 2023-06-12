package com.mao.tytmistake.model.exception;

import com.mao.tytmistake.model.error.CommonError;

public class PastDueTimeException extends BaseException {

    public PastDueTimeException() {
        super(CommonError.PAST_DUE_TIME);
    }
}
