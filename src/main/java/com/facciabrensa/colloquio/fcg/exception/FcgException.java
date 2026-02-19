package com.facciabrensa.colloquio.fcg.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public abstract class FcgException extends RuntimeException{
    private final HttpStatusCode status;

    protected FcgException(HttpStatusCode status, String message) {
        super(message);
        this.status = status;
    }
}
