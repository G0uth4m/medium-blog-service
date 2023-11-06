package com.goutham.mediumblogservice.entity.key;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ClapKey implements Serializable {

  @Column(name = "blog_id", nullable = false)
  private Long blogId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

}
