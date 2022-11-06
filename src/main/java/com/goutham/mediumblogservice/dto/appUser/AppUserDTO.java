package com.goutham.mediumblogservice.dto.appUser;

import lombok.Data;

@Data
public class AppUserDTO {
  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicURL;
  private String createdAt;
  private String lastModifiedAt;
}
