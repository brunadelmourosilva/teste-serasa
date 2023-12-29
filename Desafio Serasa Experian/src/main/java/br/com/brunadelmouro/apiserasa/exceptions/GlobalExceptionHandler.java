package br.com.brunadelmouro.apiserasa.exceptions;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StandardError> notFoundException(NotFoundException e) {
        var error = new StandardError(e.getMessage(), Date.from(Instant.now()));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StandardError> genericException(GenericException e) {
        var error = new StandardError(e.getMessage(), Date.from(Instant.now()));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        StringBuilder errorMessage = new StringBuilder("Validation error(s): ");
        for (FieldError fieldError : fieldErrors) {
            errorMessage.append(fieldError.getDefaultMessage()).append(", ");
        }
        errorMessage.deleteCharAt(errorMessage.length() - 2);

        var error = new StandardError(errorMessage.toString(), Date.from(Instant.now()));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> validationParameterException(HandlerMethodValidationException e) {
        var error = new StandardError(e.getMessage(), Date.from(Instant.now()));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
