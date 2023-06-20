package com.mao.tytmistake.controller.response.handling;

import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.model.exception.AlreadyExistsException;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.model.utility.ExceptionResponseUtility;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public BaseResponse<Object> handleNotFoundException(NotFoundException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException(AlreadyExistsException exception) {

        Map<String, Object> body = ExceptionResponseUtility.getErrorBody(exception);

        return BaseResponse.failed(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse<Object> handleAlreadyExistsException(MethodArgumentNotValidException exception) {

        String response = getValidationMessage(exception.getBindingResult());

        return BaseResponse.failed(response, HttpStatus.BAD_REQUEST);
    }

    private String getValidationMessage(BindingResult result) {
        List<ObjectError> objects = result.getAllErrors();
        ObjectError error = objects.get(0);
        return error.getDefaultMessage();
    }
}
