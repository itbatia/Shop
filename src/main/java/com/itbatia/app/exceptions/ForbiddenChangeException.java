package com.itbatia.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenChangeException extends RuntimeException {
    public ForbiddenChangeException(String msg) {
        super(msg);
    }
}
