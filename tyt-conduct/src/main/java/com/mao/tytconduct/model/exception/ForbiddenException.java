package com.mao.tytconduct.model.exception;

import com.mao.tytconduct.model.error.CommonError;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(CommonError.FORBIDDEN);
    }
}
