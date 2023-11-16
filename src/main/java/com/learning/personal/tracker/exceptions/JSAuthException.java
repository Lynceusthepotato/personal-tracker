package com.learning.personal.tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JSAuthException extends RuntimeException{
    public JSAuthException(String message) {
        super(message);
    }
}
