package com.goutham.mediumblogservice.entity;

import com.goutham.mediumblogservice.entity.key.ClapKey;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "clap")
public class Clap {

  @EmbeddedId
  private ClapKey clapKey;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

}
