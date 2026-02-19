package com.facciabrensa.colloquio.fcg.exception;

import org.springframework.http.HttpStatus;

public class FcgNotFoundException extends FcgException{

    public FcgNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
