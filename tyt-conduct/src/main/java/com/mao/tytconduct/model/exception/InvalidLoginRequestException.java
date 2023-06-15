package com.mao.tytconduct.model.exception;

import com.mao.tytconduct.model.error.CommonError;

import java.io.Serial;

public class InvalidLoginRequestException extends BaseException {

    @Serial
    private static final long serialVersionUID = -6017345433456209919L;

    public InvalidLoginRequestException() {
        super(CommonError.INVALID_USER_INFO);
    }
}
