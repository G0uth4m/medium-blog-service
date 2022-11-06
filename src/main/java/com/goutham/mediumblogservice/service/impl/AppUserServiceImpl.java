package com.goutham.mediumblogservice.service.impl;

import com.goutham.mediumblogservice.entity.AppUser;
import com.goutham.mediumblogservice.exception.DuplicateResourceException;
import com.goutham.mediumblogservice.exception.ResourceNotFoundException;
import com.goutham.mediumblogservice.repository.AppUserRepository;
import com.goutham.mediumblogservice.service.AppUserService;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

  private final AppUserRepository appUserRepository;

  @Override
  public AppUser createUser(AppUser appUser) {
    if (appUserRepository.existsByUsername(appUser.getUsername())) {
      log.error("Username: {} already taken", appUser.getUsername());
      throw new DuplicateResourceException("Username already taken");
    }
    LocalDateTime now = LocalDateTime.now();
    appUser.setCreatedAt(now);
    appUser.setLastModifiedAt(now);
    return appUserRepository.save(appUser);
  }

  @Override
  public AppUser updateUser(Long userId, AppUser appUser) {
    AppUser user = appUserRepository.findById(userId).orElseThrow(() -> {
      log.error("User: {} does not exist", userId);
      return new ResourceNotFoundException("User does not exist");
    });
    user.setFirstName(appUser.getFirstName());
    user.setLastName(appUser.getLastName());
    user.setProfilePicURL(appUser.getProfilePicURL());
    user.setLastModifiedAt(LocalDateTime.now());
    return appUserRepository.save(user);
  }

  @Override
  public AppUser getUser(Long userId) {
    return appUserRepository.findById(userId).orElseThrow(() -> {
      log.error("User: {} does not exist", userId);
      return new ResourceNotFoundException("User does not exist");
    });
  }

  @Override
  public Page<AppUser> getUsers(Pageable pageable) {
    return appUserRepository.findAll(pageable);
  }

  @Override
  public void deleteUser(Long id) {
    appUserRepository.findById(id).orElseThrow(() -> {
      log.error("User: {} does not exist", id);
      return new ResourceNotFoundException("User does not exist");
    });
    appUserRepository.deleteById(id);
  }

  @Override
  public Boolean isUserExists(Long userId) {
    return appUserRepository.findById(userId).isPresent();
  }
}
