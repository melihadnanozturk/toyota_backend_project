package com.mao.tytmistake.controller.response.handling;

import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.model.exception.auth.ForbiddenException;
import com.mao.tytmistake.model.exception.auth.NotValidTokenForUserException;
import com.mao.tytmistake.model.exception.auth.PastDueTimeException;
import com.mao.tytmistake.model.utility.ExceptionResponseUtility;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(NotValidTokenForUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseResponse<Object> handleNotFoundException(NotValidTokenForUserException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public BaseResponse<Object> handleNotFoundException(ForbiddenException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PastDueTimeException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException(PastDueTimeException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.EXPECTATION_FAILED);
    }
}
