package com.goutham.mediumblogservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.goutham.mediumblogservice.dto.appUser.AppUserCreationDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserUpdationDTO;
import com.goutham.mediumblogservice.dto.blog.BlogDTO;
import com.goutham.mediumblogservice.service.AppUserService;
import com.goutham.mediumblogservice.service.BlogService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
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
  private final BlogService blogService;

  @PostMapping
  public EntityModel<AppUserDTO> createUser(
      @Valid @RequestBody AppUserCreationDTO appUserCreationDTO) {
    AppUserDTO user = appUserService.createUser(appUserCreationDTO);
    return EntityModel.of(user,
        linkTo(methodOn(AppUserController.class).getUser(user.getUserId())).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @PutMapping("/{userId}")
  public EntityModel<AppUserDTO> updateUser(@PathVariable Long userId,
      @Valid @RequestBody AppUserUpdationDTO appUserUpdationDTO) {
    AppUserDTO user = appUserService.updateUser(userId, appUserUpdationDTO);
    return EntityModel.of(user,
        linkTo(methodOn(AppUserController.class).getUser(user.getUserId())).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @GetMapping("/{userId}")
  public EntityModel<AppUserDTO> getUser(@PathVariable Long userId) {
    AppUserDTO user = appUserService.getUser(userId);
    return EntityModel.of(user,
        linkTo(methodOn(AppUserController.class).getUser(userId)).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @GetMapping
  public CollectionModel<EntityModel<AppUserDTO>> getUsers(Pageable pageable) {
    List<AppUserDTO> appUsers = appUserService.getUsers(pageable);
    List<EntityModel<AppUserDTO>> users = appUsers.stream().map(
            user -> EntityModel.of(user,
                linkTo(methodOn(AppUserController.class).getUser(user.getUserId())).withSelfRel(),
                linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel(
                    "users")))
        .collect(Collectors.toList());
    return CollectionModel.of(users,
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withSelfRel());
  }

  @DeleteMapping("/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    appUserService.deleteUser(userId);
  }

  @GetMapping("/{userId}/blogs")
  public CollectionModel<EntityModel<BlogDTO>> getUserBlogs(@PathVariable Long userId,
      Pageable pageable) {
    List<BlogDTO> blogDTOS = blogService.getUserBlogs(userId, pageable);
    List<EntityModel<BlogDTO>> blogs = blogDTOS.stream()
        .map(blog -> EntityModel.of(blog,
            linkTo(methodOn(BlogController.class).getBlog(blog.getBlogId())).withSelfRel(),
            linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs")))
        .collect(Collectors.toList());

    return CollectionModel.of(blogs,
        linkTo(methodOn(AppUserController.class).getUserBlogs(userId,
            Pageable.unpaged())).withSelfRel());
  }
}
