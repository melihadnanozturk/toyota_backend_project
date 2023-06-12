package com.mao.tytconduct.controller.response.exceptionHandling;

import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.model.exception.BaseException;
import com.mao.tytconduct.model.exception.ForbiddenException;
import com.mao.tytconduct.model.exception.NotValidTokenForUserException;
import com.mao.tytconduct.model.exception.PastDueTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(NotValidTokenForUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseResponse<Object> handleNotFoundException(NotValidTokenForUserException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public BaseResponse<Object> handleNotFoundException(ForbiddenException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PastDueTimeException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException(PastDueTimeException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.FORBIDDEN);
    }

    private Map<String, Object> getErrorBody(BaseException exception) {

        Map<String, Object> body = new HashMap<>();
        body.put("error", exception.getError());
        body.put("message", exception.getMessage());

        return body;
    }
}