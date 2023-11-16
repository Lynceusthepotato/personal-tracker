package com.learning.personal.tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JSBadRequestException extends RuntimeException{

    public JSBadRequestException(String message) {
        super(message);
    }
}
