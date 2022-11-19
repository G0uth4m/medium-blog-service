package com.goutham.mediumblogservice.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "blog")
public class Blog {

  @Id
  @Column(name = "blog_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long blogId;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @ManyToOne
  private AppUser author;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "last_modified_at")
  private LocalDateTime lastModifiedAt;
}
