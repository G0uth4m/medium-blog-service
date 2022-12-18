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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now(ZoneOffset.UTC))
        .status(HttpStatus.UNAUTHORIZED.value())
        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
        .message(authException.getMessage())
        .path(request.getRequestURI())
        .build();
    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}