package com.boubeidev.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustumExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiError> handlerExceptionPersonNotFound(EntityNotFoundException p){
    ApiError apiErr = new ApiError();

    apiErr.setMessage(p.getMessage());
    apiErr.setCode(HttpStatus.NOT_FOUND.value());
    apiErr.setTimestamp(LocalDate.now());

    return new ResponseEntity<>(apiErr, HttpStatus.NOT_FOUND);
  }

  // Capture les erreurs de validation (@Valid / @NotBlank / @Size...)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
      errors.put(error.getField(), error.getDefaultMessage())
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  // Capture les conflits métiers (exemple : email déjà existant)
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handlerException(Exception e){
    ApiError apiErr = new ApiError();

    apiErr.setMessage(e.getMessage());
    apiErr.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    apiErr.setTimestamp(LocalDate.now());

    return new ResponseEntity<>(apiErr, HttpStatus.INTERNAL_SERVER_ERROR);
  }


}
