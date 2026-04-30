package com.example.docker_demo_springboot_app.exceptions;

import com.example.docker_demo_springboot_app.error.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> movieAlreadyExistsException(
            MovieAlreadyExistsException ex,
            HttpServletRequest request
    ){
        ErrorMessage error = ErrorMessage
                .builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.ALREADY_REPORTED.value())
                .error(HttpStatus.ALREADY_REPORTED.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorMessage> movieNotFoundException(
            MovieNotFoundException ex,
            HttpServletRequest request
    ){
        ErrorMessage error = ErrorMessage
                .builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> reviewAlreadyExistsException(
            ReviewAlreadyExistsException ex,
            HttpServletRequest request
    ){
        ErrorMessage error = ErrorMessage
                .builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.ALREADY_REPORTED.value())
                .error(HttpStatus.ALREADY_REPORTED.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.ALREADY_REPORTED);
    }
}
