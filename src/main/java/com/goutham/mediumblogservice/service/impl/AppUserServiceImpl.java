package com.goutham.mediumblogservice.service.impl;

import com.goutham.mediumblogservice.dto.appUser.AppUserCreationDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserUpdationDTO;
import com.goutham.mediumblogservice.entity.AppUser;
import com.goutham.mediumblogservice.exception.DuplicateResourceException;
import com.goutham.mediumblogservice.exception.ResourceNotFoundException;
import com.goutham.mediumblogservice.repository.AppUserRepository;
import com.goutham.mediumblogservice.service.AppUserService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

  private final ModelMapper modelMapper;

  private final AppUserRepository appUserRepository;

  @Override
  public AppUserDTO createUser(AppUserCreationDTO appUserCreationDTO) {
    if (appUserRepository.existsByUsername(appUserCreationDTO.getUsername())) {
      log.error("Username: {} already taken", appUserCreationDTO.getUsername());
      throw new DuplicateResourceException("Username already taken");
    }
    AppUser appUser = modelMapper.map(appUserCreationDTO, AppUser.class);
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    appUser.setCreatedAt(now);
    appUser.setLastModifiedAt(now);
    appUser = appUserRepository.save(appUser);
    return modelMapper.map(appUser, AppUserDTO.class);
  }

  @Override
  public AppUserDTO updateUser(Long userId, AppUserUpdationDTO appUserUpdationDTO) {
    AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> {
      log.error("User: {} does not exist", userId);
      return new ResourceNotFoundException("User does not exist");
    });
    appUser.setFirstName(appUserUpdationDTO.getFirstName());
    appUser.setLastName(appUserUpdationDTO.getLastName());
    appUser.setProfilePicURL(appUserUpdationDTO.getProfilePicURL());
    appUser.setLastModifiedAt(LocalDateTime.now(ZoneOffset.UTC));
    appUser = appUserRepository.save(appUser);
    return modelMapper.map(appUser, AppUserDTO.class);
  }

  @Override
  public AppUserDTO getUser(Long userId) {
    AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> {
      log.error("User: {} does not exist", userId);
      return new ResourceNotFoundException("User does not exist");
    });
    return modelMapper.map(appUser, AppUserDTO.class);
  }

  @Override
  public List<AppUserDTO> getUsers(Pageable pageable) {
    Page<AppUser> appUserPage = appUserRepository.findAll(pageable);
    return appUserPage.stream()
        .map(appUser -> modelMapper.map(appUser, AppUserDTO.class))
        .collect(Collectors.toList());
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

  @Override
  public AppUser getUserDAO(Long userId) {
    return appUserRepository.findById(userId).orElseThrow(() -> {
      log.error("User: {} does not exist", userId);
      return new ResourceNotFoundException("User does not exist");
    });
  }
}
