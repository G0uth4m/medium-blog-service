package com.goutham.mediumblogservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.goutham.mediumblogservice.dto.blog.BlogCreationDTO;
import com.goutham.mediumblogservice.dto.blog.BlogUpdationDTO.BlogDTO;
import com.goutham.mediumblogservice.dto.blog.BlogUpdationDTO;
import com.goutham.mediumblogservice.entity.Blog;
import com.goutham.mediumblogservice.service.BlogService;
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
@RequestMapping("/v1/api/blogs")
public class BlogController {

  private final BlogService blogService;
  private final ModelMapper modelMapper;

  @GetMapping("/{blogId}")
  public EntityModel<BlogDTO> getBlog(@PathVariable Long blogId) {
    Blog blogEntity = blogService.getBlog(blogId);
    BlogDTO blog = modelMapper.map(blogEntity, BlogDTO.class);
    return EntityModel.of(blog,
        linkTo(methodOn(BlogController.class).getBlog(blog.getId())).withSelfRel(),
        linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs"));
  }

  @GetMapping
  public CollectionModel<EntityModel<BlogDTO>> getBlogs(Pageable pageable) {
    Page<Blog> blogsPage = blogService.getAllBlogs(pageable);
    List<EntityModel<BlogDTO>> blogs = blogsPage.stream()
        .map(blog -> modelMapper.map(blog, BlogDTO.class))
        .map(blogDTO -> EntityModel.of(blogDTO,
            linkTo(methodOn(BlogController.class).getBlog(blogDTO.getId())).withSelfRel(),
            linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs")))
        .collect(Collectors.toList());

    return CollectionModel.of(blogs,
        linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withSelfRel());
  }

  @PostMapping
  public EntityModel<BlogDTO> createBlog(@Valid @RequestBody BlogCreationDTO blogCreationDTO) {
    Blog blogEntity = modelMapper.map(blogCreationDTO, Blog.class);
    blogEntity = blogService.createBlog(blogEntity, blogCreationDTO.getAuthorId());
    BlogDTO blog = modelMapper.map(blogEntity, BlogDTO.class);
    return EntityModel.of(blog,
        linkTo(methodOn(BlogController.class).getBlog(blog.getId())).withSelfRel(),
        linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs"));
  }

  @PutMapping("/{blogId}")
  public EntityModel<BlogDTO> editBlog(@PathVariable Long blogId, @Valid @RequestBody
      BlogUpdationDTO blogUpdationDTO) {
    Blog blogEntity = modelMapper.map(blogUpdationDTO, Blog.class);
    blogEntity = blogService.editBlog(blogId, blogEntity);
    BlogDTO blog = modelMapper.map(blogEntity, BlogDTO.class);
    return EntityModel.of(blog,
        linkTo(methodOn(BlogController.class).getBlog(blog.getId())).withSelfRel(),
        linkTo(methodOn(BlogController.class).getBlogs(Pageable.unpaged())).withRel("blogs"));
  }

  @DeleteMapping("/{blogId}")
  public void deleteBlog(@PathVariable Long blogId) {
    blogService.deleteBlog(blogId);
  }
}
