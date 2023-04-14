package com.mao.tytmistake.model.exception;

import com.mao.tytmistake.model.error.CommonError;
import lombok.Getter;

import java.io.Serial;

@Getter
public class AlreadyExistsException extends BaseException {

    @Serial
    private static final long serialVersionUID = -6247003375990699285L;

    public AlreadyExistsException(String field) {
        super(CommonError.ALREADY_EXIST, field);
    }
}
