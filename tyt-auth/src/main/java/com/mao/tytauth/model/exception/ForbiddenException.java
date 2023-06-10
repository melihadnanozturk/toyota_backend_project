package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.CommonError;

public class ForbiddenException extends BaseException {

    public ForbiddenException(String field) {
        super(CommonError.FORBIDDEN, field);
    }
}
