package com.mao.tytmistake.model.exception;

import com.mao.tytmistake.model.error.CommonError;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(CommonError.FORBIDDEN);
    }
}
