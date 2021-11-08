package learn.chess.controllers;

import learn.chess.domain.Result;
import learn.chess.domain.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ErrorResponse {

    public static <T> ResponseEntity<Object> build(Result<T> result) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (result.getType() == null || result.getType() == ResultType.INVALID) {
            status = HttpStatus.BAD_REQUEST;
        } else if (result.getType() == ResultType.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        }
        return build(result.getMessages(), status);
    }

    public static ResponseEntity<Object> build(String message, HttpStatus status) {
        return build(List.of(message), status);
    }

    public static ResponseEntity<Object> build(List<String> messages, HttpStatus status) {
        return new ResponseEntity<>(messages, status);
    }
}
