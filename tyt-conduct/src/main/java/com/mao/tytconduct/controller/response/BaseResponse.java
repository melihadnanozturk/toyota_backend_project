package com.mao.tytconduct.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class BaseResponse<T> {

    public HttpStatus httpStatus;

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

    public static <T> BaseResponse<T> isSuccess() {
        return BaseResponse.<T>builder()
                .httpStatus(HttpStatus.OK)
                .isSuccess(ResponseStatus.SUCCESS)
                .build();
    }

    public final BaseResponse<String> failed(String message, HttpStatus httpStatus) {
        return BaseResponse.<String>builder()
                .httpStatus(httpStatus)
                .isSuccess(ResponseStatus.FAILED)
                .response(message)
                .build();
    }


    public enum ResponseStatus {
        SUCCESS("SUCCESS"),
        FAILED("FAILED");

        private String message;

        ResponseStatus(String message) {
            this.message = message;
        }

    }
}
