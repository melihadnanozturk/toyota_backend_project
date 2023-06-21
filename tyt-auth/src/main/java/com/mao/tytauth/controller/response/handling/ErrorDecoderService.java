package com.mao.tytauth.controller.response.handling;

import com.mao.tytauth.model.exception.InvalidLoginRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

/**
 * This class catches errors thrown in microservice
 */
@Component
public class ErrorDecoderService implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        if (response.status() == 400) {
            return new InvalidLoginRequestException();
        }
        return new Exception();
    }
}
