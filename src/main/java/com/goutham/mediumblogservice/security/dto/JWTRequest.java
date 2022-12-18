package com.goutham.mediumblogservice.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTRequest {
  private String username;
  private String password;
}
