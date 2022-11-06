package com.goutham.mediumblogservice.dto.appUser;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppUserCreationDTO {

  @NotBlank(message = "Username cannot be empty")
  private String username;

  @NotBlank(message = "First Name cannot be empty")
  private String firstName;

  @NotBlank(message = "Last Name cannot be empty")
  private String lastName;

  @NotBlank(message = "Password cannot be empty")
  private String password;

  @NotBlank(message = "Email cannot be empty")
  private String email;

  private String profilePicURL;
}
