package com.mao.tytconduct.model.exception;

import com.mao.tytconduct.model.error.CommonError;
import lombok.Getter;

import java.io.Serial;

/**
 * This exception is thrown when record exists already
 */
@Getter
public class AlreadyExistsException extends BaseException {

    @Serial
    private static final long serialVersionUID = -6247003375990699285L;

    public AlreadyExistsException(String field) {
        super(CommonError.ALREADY_EXIST, field);
    }
}
