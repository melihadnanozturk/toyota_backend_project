package com.mao.tytmistake.model.exception.auth;

import com.mao.tytmistake.model.error.AuthError;
import com.mao.tytmistake.model.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * This exception is thrown when the Client does not have the valid token
 */
@Getter
@Setter
public class NotValidTokenForUserException extends BaseException {

    @Serial
    private static final long serialVersionUID = -1175370455482142205L;

    public NotValidTokenForUserException(String field) {
        super(AuthError.NOT_VALID_TOKEN, field);
    }
}
