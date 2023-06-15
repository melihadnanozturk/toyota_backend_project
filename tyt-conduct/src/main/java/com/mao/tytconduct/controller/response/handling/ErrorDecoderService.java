package com.mao.tytconduct.controller.response.handling;

import com.mao.tytconduct.model.exception.auth.ForbiddenException;
import com.mao.tytconduct.model.exception.auth.NotValidTokenForUserException;
import com.mao.tytconduct.model.exception.auth.PastDueTimeException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ErrorDecoderService implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        switch (response.status()) {
            case 401 -> {
                String userName = getUserName(response);
                return new NotValidTokenForUserException(userName);
            }
            case 403 -> {
                return new ForbiddenException();
            }
            case 417 -> {
                return new PastDueTimeException();
            }
            default -> {
                return new RuntimeException("Something is wrong");
            }
        }
    }

    private String getUserName(Response response) {
        List<String> userName = (List<String>) response.request().headers().get("userName");
        return userName.get(0);
    }
}
