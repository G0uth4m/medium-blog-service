package com.goutham.mediumblogservice.dto.blog;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlogUpdationDTO {

  @NotBlank(message = "Title cannot be empty")
  private String title;

  @NotBlank(message = "Content cannot be empty")
  private String content;

}
