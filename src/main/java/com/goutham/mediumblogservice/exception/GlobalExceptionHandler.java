package com.goutham.mediumblogservice.exception;

import com.goutham.mediumblogservice.response.ErrorResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());
    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now(ZoneOffset.UTC))
        .status(status.value())
        .error(status.getReasonPhrase())
        .message(errors).path(((ServletWebRequest) request).getRequest().getRequestURI())
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
