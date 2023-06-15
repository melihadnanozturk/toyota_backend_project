package com.mao.tytmistake.model.exception;

import com.mao.tytmistake.model.error.AuthError;
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
