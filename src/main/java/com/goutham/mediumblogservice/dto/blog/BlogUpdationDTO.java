package com.goutham.mediumblogservice.dto.blog;

import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlogUpdationDTO {

  @NotBlank(message = "Title cannot be empty")
  private String title;

  @NotBlank(message = "Content cannot be empty")
  private String content;

  @Data
  public static class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private AppUserDTO author;
  }
}
