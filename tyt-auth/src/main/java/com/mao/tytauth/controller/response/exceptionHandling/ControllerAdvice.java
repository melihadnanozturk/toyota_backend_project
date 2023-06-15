package com.mao.tytauth.controller.response.exceptionHandling;

import com.mao.tytauth.controller.response.BaseResponse;
import com.mao.tytauth.model.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
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

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException() {

        Map<String, Object> body = getErrorBody(new PastDueTimeException());

        return BaseResponse.failed(body, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException(NotFoundException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLoginRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException(InvalidLoginRequestException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getErrorBody(BaseException exception) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error_code", exception.getError().getCode());
        body.put("error", exception.getError());
        body.put("message", exception.getMessage());

        return body;
    }
}
