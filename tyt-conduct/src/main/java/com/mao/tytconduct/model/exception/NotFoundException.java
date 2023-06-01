package com.mao.tytconduct.model.exception;

import com.mao.tytconduct.model.error.CommonError;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class NotFoundException extends BaseException {

    @Serial
    private static final long serialVersionUID = -6965547598276886042L;

    public NotFoundException(String field) {
        super(CommonError.NOT_FOUND, field);
    }
}
