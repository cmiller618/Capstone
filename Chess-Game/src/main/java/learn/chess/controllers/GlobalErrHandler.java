package learn.chess.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalErrHandler {

    @ExceptionHandler
    public ResponseEntity<String> handle(Exception ex) {
        return new ResponseEntity<>("Can't show you the details, but something went wrong on our end.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handle(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                List.of(ex.getMostSpecificCause().getMessage()),
                HttpStatus.BAD_REQUEST);

    }
}
