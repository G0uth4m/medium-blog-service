package com.goutham.mediumblogservice.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goutham.mediumblogservice.response.ErrorResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now(ZoneOffset.UTC))
        .status(HttpStatus.FORBIDDEN.value())
        .error(HttpStatus.FORBIDDEN.getReasonPhrase())
        .message(accessDeniedException.getMessage())
        .path(request.getRequestURI())
        .build();
    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
