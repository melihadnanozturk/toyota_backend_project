package com.mao.tytmistake.controller.response.handling;

import com.mao.tytmistake.model.exception.auth.ForbiddenException;
import com.mao.tytmistake.model.exception.auth.NotValidTokenForUserException;
import com.mao.tytmistake.model.exception.auth.PastDueTimeException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class catches exceptions thrown in feign-client
 */
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
                return new Exception();
            }
        }
    }

    private String getUserName(Response response) {
        List<String> userName = (List<String>) response.request().headers().get("userName");
        return userName.get(0);
    }
}
