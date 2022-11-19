package com.goutham.mediumblogservice.service;

import com.goutham.mediumblogservice.dto.appUser.AppUserCreationDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserUpdationDTO;
import com.goutham.mediumblogservice.entity.AppUser;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AppUserService {
  AppUserDTO createUser(AppUserCreationDTO appUserCreationDTO);
  AppUserDTO updateUser(Long userId, AppUserUpdationDTO appUserUpdationDTO);
  AppUserDTO getUser(Long userId);
  List<AppUserDTO> getUsers(Pageable pageable);
  void deleteUser(Long id);
  Boolean isUserExists(Long userId);
  AppUser getUserDAO(Long userId);
}
