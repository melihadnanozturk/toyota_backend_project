package com.mao.tytconduct.model.exception.auth;

import com.mao.tytconduct.model.error.AuthError;
import com.mao.tytconduct.model.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class NotValidTokenForUserException extends BaseException {

    @Serial
    private static final long serialVersionUID = -1175370455482142205L;

    public NotValidTokenForUserException(String field) {
        super(AuthError.NOT_VALID_TOKEN, field);
    }
}
