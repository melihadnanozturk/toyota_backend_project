package com.mao.tytconduct.controller.response.handling;

import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.model.exception.auth.ForbiddenException;
import com.mao.tytconduct.model.exception.auth.InvalidLoginRequestException;
import com.mao.tytconduct.model.exception.auth.NotValidTokenForUserException;
import com.mao.tytconduct.model.exception.auth.PastDueTimeException;
import com.mao.tytconduct.model.utility.ExceptionResponseUtility;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * This class catches thrown exceptions about auth in module
 */
@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(NotValidTokenForUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseResponse<Object> handleNotValidTokenForUserException(NotValidTokenForUserException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public BaseResponse<Object> handleForbiddenException(ForbiddenException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PastDueTimeException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public BaseResponse<Object> handlePastDueTimeException(PastDueTimeException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(InvalidLoginRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleInvalidLoginRequestException(InvalidLoginRequestException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.BAD_REQUEST);
    }


}
