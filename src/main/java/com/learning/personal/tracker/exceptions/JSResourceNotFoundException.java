package com.learning.personal.tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JSResourceNotFoundException extends RuntimeException{
    public JSResourceNotFoundException(String message) {
        super(message);
    }
}
