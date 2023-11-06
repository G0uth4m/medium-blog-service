package com.goutham.mediumblogservice.dto.appUser;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(collectionRelation = "users", itemRelation = "user")
public class AppUserDTO {
  private Long userId;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicURL;
  private LocalDateTime createdAt;
  private LocalDateTime lastModifiedAt;
}
