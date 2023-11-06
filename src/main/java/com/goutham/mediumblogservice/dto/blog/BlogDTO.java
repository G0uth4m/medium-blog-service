package com.goutham.mediumblogservice.dto.blog;

import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Data
@AllArgsConstructor
@Relation(collectionRelation = "blogs", itemRelation = "blog")
public class BlogDTO {
  private Long blogId;
  private String title;
  private String content;
  private String author;
  private Long claps;
  private LocalDateTime createdAt;
  private LocalDateTime lastModifiedAt;
}
