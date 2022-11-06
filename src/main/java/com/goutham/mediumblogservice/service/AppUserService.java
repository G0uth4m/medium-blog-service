package com.goutham.mediumblogservice.service;

import com.goutham.mediumblogservice.entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppUserService {
  AppUser createUser(AppUser appUser);
  AppUser updateUser(Long userId, AppUser appUser);
  AppUser getUser(Long userId);
  Page<AppUser> getUsers(Pageable pageable);
  void deleteUser(Long id);
  Boolean isUserExists(Long userId);
}
