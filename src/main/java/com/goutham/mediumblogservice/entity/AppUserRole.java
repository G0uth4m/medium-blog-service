package com.goutham.mediumblogservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "app_user_role")
public class AppUserRole {

  @Id
  @Column(name = "role_id")
  private Integer roleId;

  @Column(name = "role_name")
  private String roleName;
}
