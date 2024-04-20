package com.logicea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_ACCEPTABLE)
public class InvalidColorFormatException extends RuntimeException {
    public InvalidColorFormatException(String message) {
        super(message);
    }
}
