package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.CommonError;

public class InvalidLoginRequestException extends BaseException {

    public InvalidLoginRequestException() {
        super(CommonError.INVALID_USER_INFO);
    }
}
