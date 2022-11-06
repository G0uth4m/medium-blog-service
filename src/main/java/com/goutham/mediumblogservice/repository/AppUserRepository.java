package com.goutham.mediumblogservice.repository;

import com.goutham.mediumblogservice.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  Boolean existsByUsername(String username);
}
