package com.goutham.mediumblogservice.entity;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "profile_pic_url")
  private String profilePicURL;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<AppUserRole> roles;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "last_modified_at")
  private LocalDateTime lastModifiedAt;
}
