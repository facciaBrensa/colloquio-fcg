package com.facciabrensa.colloquio.fcg.exception;

import com.facciabrensa.colloquio.fcg.dto.FcgErrorDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FcgExceptionHandler {

    @ExceptionHandler(FcgException.class)
    public FcgErrorDTO handleFcgException(FcgException exception, HttpServletResponse response) {
        HttpStatusCode statusCode = exception.getStatus();
        response.setStatus(statusCode.value());

        return new FcgErrorDTO(
                statusCode.value(),
                exception.getMessage()
        );
    }
}