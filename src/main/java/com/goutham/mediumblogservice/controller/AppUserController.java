package com.goutham.mediumblogservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.goutham.mediumblogservice.dto.appUser.AppUserCreationDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserUpdationDTO;
import com.goutham.mediumblogservice.entity.AppUser;
import com.goutham.mediumblogservice.service.AppUserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/users")
public class AppUserController {

  private final AppUserService appUserService;
  private final ModelMapper modelMapper;

  @PostMapping
  public EntityModel<AppUserDTO> createUser(
      @Valid @RequestBody AppUserCreationDTO appUserCreationDTO) {
    AppUser appUser = modelMapper.map(appUserCreationDTO, AppUser.class);
    appUser = appUserService.createUser(appUser);
    AppUserDTO appUserDTO = modelMapper.map(appUser, AppUserDTO.class);
    return EntityModel.of(appUserDTO,
        linkTo(methodOn(AppUserController.class).getUser(appUserDTO.getId())).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @PutMapping("/{id}")
  public EntityModel<AppUserDTO> updateUser(@PathVariable Long id,
      @Valid @RequestBody AppUserUpdationDTO appUserUpdationDTO) {
    AppUser appUser = modelMapper.map(appUserUpdationDTO, AppUser.class);
    appUser = appUserService.updateUser(id, appUser);
    AppUserDTO appUserDTO = modelMapper.map(appUser, AppUserDTO.class);
    return EntityModel.of(appUserDTO,
        linkTo(methodOn(AppUserController.class).getUser(appUser.getId())).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @GetMapping("/{id}")
  public EntityModel<AppUserDTO> getUser(@PathVariable Long id) {
    AppUser appUser = appUserService.getUser(id);
    AppUserDTO appUserDTO = modelMapper.map(appUser, AppUserDTO.class);
    return EntityModel.of(appUserDTO,
        linkTo(methodOn(AppUserController.class).getUser(id)).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @GetMapping
  public CollectionModel<EntityModel<AppUserDTO>> getUsers(Pageable pageable) {
    Page<AppUser> appUsers = appUserService.getUsers(pageable);
    List<AppUserDTO> appUserDTOS = appUsers.stream()
        .map(appUser -> modelMapper.map(appUser, AppUserDTO.class)).collect(Collectors.toList());
    List<EntityModel<AppUserDTO>> appUserEntityModels = appUserDTOS.stream().map(
            appUserDTO -> EntityModel.of(appUserDTO,
                linkTo(methodOn(AppUserController.class).getUser(appUserDTO.getId())).withSelfRel(),
                linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel(
                    "users")))
        .collect(Collectors.toList());
    return CollectionModel.of(appUserEntityModels,
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withSelfRel());
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    appUserService.deleteUser(id);
  }
}
