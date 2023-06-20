package com.mao.tytmistake.model.utility;

import com.mao.tytmistake.model.error.TytError;
import com.mao.tytmistake.model.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ExceptionResponseUtility {

    public static Logger logger = LogManager.getLogger(ExceptionResponseUtility.class);

    public static Map<String, Object> getErrorBody(BaseException exception) {
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
}
