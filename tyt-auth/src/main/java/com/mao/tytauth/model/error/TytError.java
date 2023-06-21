package com.mao.tytauth.model.error;

/**
 * Interface representing an error in the TYT (Toyota-Backend) system.
 */
public interface TytError {

    /**
     * Returns Error code associated with the error.
     *
     * @return Error code
     */
    Integer getCode();

    /**
     * Returns Error message describing the error.
     *
     * @return Error message
     */
    String getMessage();
}
