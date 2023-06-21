package com.mao.tytauth.controller.response.handling;

import com.mao.tytauth.controller.response.BaseResponse;
import com.mao.tytauth.model.error.TytError;
import com.mao.tytauth.model.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * This class catches thrown exceptions in module
 */
@RestControllerAdvice
public class ControllerAdvice {

    private final Logger logger = LogManager.getLogger(ControllerAdvice.class);

    @ExceptionHandler(NotValidTokenForUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseResponse<Object> handleNotValidTokenForUserException(NotValidTokenForUserException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public BaseResponse<Object> handleForbiddenException(ForbiddenException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public BaseResponse<Object> handleExpiredJwtException() {

        Map<String, Object> body = getErrorBody(new PastDueTimeException());

        return BaseResponse.failed(body, HttpStatus.EXPECTATION_FAILED);
    }


    @ExceptionHandler(InvalidLoginRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleInvalidLoginRequestException(InvalidLoginRequestException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseResponse<Object> handleSignatureException() {

        Map<String, Object> body = getErrorBody(new NotValidTokenForUserException());

        return BaseResponse.failed(body, HttpStatus.UNAUTHORIZED);
    }

    private Map<String, Object> getErrorBody(BaseException exception) {
        TytError error = exception.getError();
        String message = exception.getMessage();
        Integer errorCode = exception.getError().getCode();
        String logMessage = "Error : {};   ExceptionMessage : {};   ErrorCode : {};";

        logger.atError().log(logMessage, error, message, errorCode);

        Map<String, Object> body = new HashMap<>();
        body.put("error_code", exception.getError().getCode());
        body.put("error", exception.getError());
        body.put("message", exception.getMessage());

        return body;
    }
}
