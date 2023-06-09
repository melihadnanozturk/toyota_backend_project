package com.mao.tytauth.model.exception;

import com.mao.tytauth.model.error.CommonError;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class NotValidUserException extends BaseException {

    @Serial
    private static final long serialVersionUID = -1175370455482142205L;

    public NotValidUserException(String field) {
        super(CommonError.NOT_VALID_USER, field);
    }
}
