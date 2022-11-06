package com.goutham.mediumblogservice.dto.appUser;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppUserUpdationDTO {

  @NotBlank(message = "First name cannot be empty")
  private String firstName;

  @NotBlank(message = "Last name cannot be empty")
  private String lastName;

  private String profilePicURL;
}
