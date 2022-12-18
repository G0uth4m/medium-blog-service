package com.goutham.mediumblogservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.goutham.mediumblogservice.dto.blog.BlogCreationDTO;
import com.goutham.mediumblogservice.dto.blog.BlogDTO;
import com.goutham.mediumblogservice.dto.blog.BlogUpdationDTO;
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
@RequestMapping("/v1/api/blogs")
@SecurityRequirement(name = "Bearer Authentication")
public class BlogController {

  private final BlogService blogService;

  @GetMapping("/{blogId}")
  public EntityModel<BlogDTO> getBlog(@PathVariable Long blogId) {
    BlogDTO blog = blogService.getBlog(blogId);
    return EntityModel.of(blog,
        linkTo(methodOn(BlogController.class).getBlog(blog.getBlogId())).withSelfRel(),
        linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs"));
  }

  @GetMapping
  public CollectionModel<EntityModel<BlogDTO>> getBlogs(@ParameterObject Pageable pageable) {
    List<BlogDTO> blogDTOS = blogService.getAllBlogs(pageable);
    List<EntityModel<BlogDTO>> blogs = blogDTOS.stream()
        .map(blog -> EntityModel.of(blog,
            linkTo(methodOn(BlogController.class).getBlog(blog.getBlogId())).withSelfRel(),
            linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs")))
        .collect(Collectors.toList());

    return CollectionModel.of(blogs,
        linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withSelfRel());
  }

  @PostMapping
  public EntityModel<BlogDTO> createBlog(@Valid @RequestBody BlogCreationDTO blogCreationDTO) {
    BlogDTO blog = blogService.createBlog(blogCreationDTO);
    return EntityModel.of(blog,
        linkTo(methodOn(BlogController.class).getBlog(blog.getBlogId())).withSelfRel(),
        linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs"));
  }

  @PutMapping("/{blogId}")
  public EntityModel<BlogDTO> editBlog(@PathVariable Long blogId, @Valid @RequestBody
      BlogUpdationDTO blogUpdationDTO) {
    BlogDTO blog = blogService.editBlog(blogId, blogUpdationDTO);
    return EntityModel.of(blog,
        linkTo(methodOn(BlogController.class).getBlog(blog.getBlogId())).withSelfRel(),
        linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs"));
  }

  @DeleteMapping("/{blogId}")
  public void deleteBlog(@PathVariable Long blogId) {
    blogService.deleteBlog(blogId);
  }

}
