package com.mao.tytconduct.controller.response;

import com.mao.tytconduct.model.exception.ForbiddenException;
import com.mao.tytconduct.model.exception.NotValidTokenForUserException;
import com.mao.tytconduct.model.exception.PastDueTimeException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        switch (response.status()) {
            case 401 -> {
                String userName = response.request().headers().get("userName").toString();
                return new NotValidTokenForUserException(userName);
            }
            case 403 -> {
                return new ForbiddenException();
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
