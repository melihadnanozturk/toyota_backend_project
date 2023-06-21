package com.mao.tytauth.model.error;

/**
 * This interface Used as baseError to provide hierarchy for Error enums
 */
public interface TytError {

    Integer getCode();

    String getMessage();
}
