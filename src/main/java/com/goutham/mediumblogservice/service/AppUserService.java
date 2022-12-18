package com.goutham.mediumblogservice.service;

import com.goutham.mediumblogservice.dto.appUser.AppUserCreationDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserUpdationDTO;
import com.goutham.mediumblogservice.entity.AppUser;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AppUserService {
  AppUserDTO createUser(AppUserCreationDTO appUserCreationDTO);
  AppUserDTO updateUser(String username, AppUserUpdationDTO appUserUpdationDTO);
  AppUserDTO getUser(String username);
  List<AppUserDTO> getUsers(Pageable pageable);
  void deleteUser(String username);
  Boolean isUserExists(String username);
  AppUser getUserDAO(String username);
}
