package com.mao.tytconduct.controller.response.handling;

import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.model.error.TytError;
import com.mao.tytconduct.model.exception.AlreadyExistsException;
import com.mao.tytconduct.model.exception.BaseException;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.model.exception.auth.InvalidLoginRequestException;
import com.mao.tytconduct.service.impl.LoginServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    private final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleNotFoundException(NotFoundException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidLoginRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleInvalidLoginRequestException(InvalidLoginRequestException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException(AlreadyExistsException exception) {

        Map<String, Object> body = getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException(MethodArgumentNotValidException exception) {

        String response = getValidationMessage(exception.getBindingResult());

        return BaseResponse.failed(response, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getErrorBody(BaseException exception) {
        TytError tytError = exception.getError();
        String message = exception.getMessage();
        Integer errorCode = exception.getError().getCode();
        String logMessage = "Error : {};   ExceptionMessage : {};   ErrorCode : {};";

        logger.atError().log(logMessage, tytError, message, errorCode);

        Map<String, Object> body = new HashMap<>();
        body.put("error", tytError);
        body.put("message", message);
        body.put("error_code", errorCode);

        return body;
    }

    private String getValidationMessage(BindingResult result) {
        List<ObjectError> objects = result.getAllErrors();
        ObjectError error = objects.get(0);
        return error.getDefaultMessage();
    }
}
