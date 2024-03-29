package com.mao.tytconduct.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This Enum is used to identify authentication related errors
 */
@Getter
@AllArgsConstructor
public enum AuthError implements TytError {

    INVALID_USER_INFO(2000, "Username or Password are invalid"),
    PAST_DUE_TIME(2001, "Token has expired"),
    NOT_VALID_TOKEN(2002, "The token is not compatible with the user. Permission denied, %s"),
    FORBIDDEN(2003, "Access Denied. You do not have permission to access the requested resource");

    private final Integer code;
    private final String message;
}
