package com.mao.tytauth.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * This class is a base class for responseClasses.Common fields for responses are inherited from here.
 * The class uses the Lombok annotations @Data and @Getter to automatically generate getters and a builder method.
 */
@Builder
@Getter
public class BaseResponse<T> {

    private HttpStatus httpStatus;

    private T response;

    private ResponseStatus isSuccess;

    @Builder.Default
    private LocalDateTime localDateTime = LocalDateTime.now();

    public static <T> BaseResponse<T> isSuccess(T response) {
        return BaseResponse.<T>builder()
                .httpStatus(HttpStatus.OK)
                .isSuccess(ResponseStatus.SUCCESS)
                .response(response)
                .build();
    }

    public static <T> BaseResponse<T> failed(T t, HttpStatus httpStatus) {
        return BaseResponse.<T>builder()
                .httpStatus(httpStatus)
                .isSuccess(ResponseStatus.FAILED)
                .response(t)
                .build();
    }


    public enum ResponseStatus {
        SUCCESS("SUCCESS"),
        FAILED("FAILED");

        final String message;

        ResponseStatus(String message) {
            this.message = message;
        }

    }
}
