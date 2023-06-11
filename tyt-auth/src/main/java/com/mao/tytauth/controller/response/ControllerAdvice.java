package com.mao.tytauth.controller.response;

import com.mao.tytauth.model.exception.BaseException;
import com.mao.tytauth.model.exception.ForbiddenException;
import com.mao.tytauth.model.exception.NotValidUserException;
import com.mao.tytauth.model.exception.PastDueTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotValidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleNotFoundException(NotValidUserException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.BAD_REQUEST);
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
        body.put("timestamp", LocalDateTime.now());
        body.put("error", exception.getError());
        body.put("message", exception.getMessage());

        return body;
    }
}
