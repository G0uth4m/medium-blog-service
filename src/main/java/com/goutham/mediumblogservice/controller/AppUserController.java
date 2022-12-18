package com.goutham.mediumblogservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.goutham.mediumblogservice.dto.appUser.AppUserCreationDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserDTO;
import com.goutham.mediumblogservice.dto.appUser.AppUserUpdationDTO;
import com.goutham.mediumblogservice.dto.blog.BlogDTO;
import com.goutham.mediumblogservice.security.dto.JWTRequest;
import com.goutham.mediumblogservice.security.dto.JWTResponse;
import com.goutham.mediumblogservice.security.util.JWTUtil;
import com.goutham.mediumblogservice.service.AppUserService;
import com.goutham.mediumblogservice.service.BlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
  private final AuthenticationManager authenticationManager;
  private final JWTUtil jwtUtil;

  @PostMapping
  public EntityModel<AppUserDTO> createUser(
      @Valid @RequestBody AppUserCreationDTO appUserCreationDTO) {
    AppUserDTO user = appUserService.createUser(appUserCreationDTO);
    return EntityModel.of(user,
        linkTo(methodOn(AppUserController.class).getUser(user.getUsername())).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("#username == authentication.principal.username")
  @PutMapping("/{username}")
  public EntityModel<AppUserDTO> updateUser(@PathVariable String username,
      @Valid @RequestBody AppUserUpdationDTO appUserUpdationDTO) {
    AppUserDTO user = appUserService.updateUser(username, appUserUpdationDTO);
    return EntityModel.of(user,
        linkTo(methodOn(AppUserController.class).getUser(user.getUsername())).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("/{username}")
  public EntityModel<AppUserDTO> getUser(@PathVariable String username) {
    AppUserDTO user = appUserService.getUser(username);
    return EntityModel.of(user,
        linkTo(methodOn(AppUserController.class).getUser(user.getUsername())).withSelfRel(),
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel("users"));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping
  public CollectionModel<EntityModel<AppUserDTO>> getUsers(@ParameterObject Pageable pageable) {
    List<AppUserDTO> appUsers = appUserService.getUsers(pageable);
    List<EntityModel<AppUserDTO>> users = appUsers.stream().map(
            user -> EntityModel.of(user,
                linkTo(methodOn(AppUserController.class).getUser(user.getUsername())).withSelfRel(),
                linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withRel(
                    "users")))
        .collect(Collectors.toList());
    return CollectionModel.of(users,
        linkTo(methodOn(AppUserController.class).getUsers(Pageable.unpaged())).withSelfRel());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("#username == authentication.principal.username or hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{username}")
  public void deleteUser(@PathVariable String username) {
    appUserService.deleteUser(username);
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("/{username}/blogs")
  public CollectionModel<EntityModel<BlogDTO>> getUserBlogs(@PathVariable String username,
      @ParameterObject Pageable pageable) {
    List<BlogDTO> blogDTOS = blogService.getUserBlogs(username, pageable);
    List<EntityModel<BlogDTO>> blogs = blogDTOS.stream()
        .map(blog -> EntityModel.of(blog,
            linkTo(methodOn(BlogController.class).getBlog(blog.getBlogId())).withSelfRel(),
            linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs")))
        .collect(Collectors.toList());

    return CollectionModel.of(blogs,
        linkTo(methodOn(AppUserController.class).getUserBlogs(username,
            Pageable.unpaged())).withSelfRel());
  }

  @PostMapping("/login")
  public EntityModel<JWTResponse> login(@RequestBody JWTRequest jwtRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
            jwtRequest.getPassword()));
    String token = jwtUtil.generateToken(authentication);
    JWTResponse jwtResponse = new JWTResponse(token);
    return EntityModel.of(jwtResponse,
        linkTo(methodOn(AppUserController.class).login(jwtRequest)).withSelfRel());
  }

}
