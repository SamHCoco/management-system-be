package com.samhcoco.managementsystem.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(InvalidInputApiException.class)
    public ResponseEntity<Error> handleInvalidInput(InvalidInputApiException e) {
        final Error error = Error.builder()
                                 .exception(e.getMessage())
                                 .errors(e.getErrors())
                                 .build();

        return ResponseEntity.status(BAD_REQUEST)
                             .body(error);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<Error> handleOutOfStock(OutOfStockException e) {
        final Error error = Error.builder()
                                 .exception(e.getMessage())
                                 .errors(e.getErrors())
                                 .build();

        return ResponseEntity.status(BAD_REQUEST)
                             .body(error);
    }

}
