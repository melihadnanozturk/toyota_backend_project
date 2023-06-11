package com.mao.tytconduct.model.exception;

import com.mao.tytconduct.model.error.CommonError;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class NotValidTokenForUserException extends BaseException {

    @Serial
    private static final long serialVersionUID = -1175370455482142205L;

    public NotValidTokenForUserException(String field) {
        super(CommonError.NOT_VALID_TOKEN, field);
    }
}
