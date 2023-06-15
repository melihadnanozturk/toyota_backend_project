package com.mao.tytauth.controller.response.exceptionHandling;

import com.mao.tytauth.model.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ErrorDecoderService implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        if (response.status() == 400) {
            String userName = getUserName(response);
            return new NotFoundException(userName);
        }
        return new Exception();
    }

    private String getUserName(Response response) {
        List<String> userName = (List<String>) response.request().headers().get("userName");
        return userName.get(0);
    }
}
