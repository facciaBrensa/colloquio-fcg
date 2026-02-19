package com.facciabrensa.colloquio.fcg.exception;

import org.springframework.http.HttpStatus;

public class FcgBadRequestException extends FcgException{

    public FcgBadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
