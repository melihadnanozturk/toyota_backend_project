package com.mao.tytconduct.controller.response;

import com.mao.tytconduct.model.exception.ForbiddenException;
import com.mao.tytconduct.model.exception.NotValidUserException;
import com.mao.tytconduct.model.exception.PastDueTimeException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    public ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        switch (response.status()) {
            case 400 -> {
                return new NotValidUserException(s);
            }
            case 403 -> {
                return new ForbiddenException(s);
            }
            case 417 -> {
                return new PastDueTimeException();
            }
            default -> {
                return new Exception();
            }
        }
    }
}
