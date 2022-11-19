package com.goutham.mediumblogservice.dto.blog;

import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(collectionRelation = "blogs", itemRelation = "blog")
public class BlogDTO {
  private Long blogId;
  private String title;
  private String content;
  private AppUserDTO author;
  private String createdAt;
  private String lastModifiedAt;
}
