package com.goutham.mediumblogservice.service.impl;

import com.goutham.mediumblogservice.dto.blog.BlogCreationDTO;
import com.goutham.mediumblogservice.dto.blog.BlogDTO;
import com.goutham.mediumblogservice.dto.blog.BlogUpdationDTO;
import com.goutham.mediumblogservice.entity.AppUser;
import com.goutham.mediumblogservice.entity.Blog;
import com.goutham.mediumblogservice.exception.ResourceNotFoundException;
import com.goutham.mediumblogservice.repository.BlogRepository;
import com.goutham.mediumblogservice.service.AppUserService;
import com.goutham.mediumblogservice.service.BlogService;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

  private ModelMapper modelMapper;
  private final BlogRepository blogRepository;
  private final AppUserService appUserService;

  @Override
  public BlogDTO createBlog(BlogCreationDTO blogCreationDTO) {
    AppUser user = appUserService.getUserDAO(blogCreationDTO.getAuthorId());
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    Blog blog = Blog.builder()
        .title(blogCreationDTO.getTitle())
        .content(blogCreationDTO.getContent())
        .author(user)
        .createdAt(now)
        .lastModifiedAt(now)
        .build();
    blog = blogRepository.save(blog);
    return modelMapper.map(blog, BlogDTO.class);
  }

  @Override
  public BlogDTO editBlog(Long blogId, BlogUpdationDTO blogUpdationDTO) {
    Blog blog = getBlogDAO(blogId);
    blog.setTitle(blogUpdationDTO.getTitle());
    blog.setContent(blogUpdationDTO.getContent());
    blog.setLastModifiedAt(LocalDateTime.now(ZoneOffset.UTC));
    blog = blogRepository.save(blog);
    return modelMapper.map(blog, BlogDTO.class);
  }

  @Override
  public BlogDTO getBlog(Long blogId) {
    Blog blog = getBlogDAO(blogId);
    return modelMapper.map(blog, BlogDTO.class);
  }

  @Override
  public List<BlogDTO> getAllBlogs(Pageable pageable) {
    return blogRepository.findAll(pageable)
        .stream()
        .map(blog -> modelMapper.map(blog, BlogDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<BlogDTO> getUserBlogs(Long authorId, Pageable pageable) {
    if (!appUserService.isUserExists(authorId)) {
      log.error("User: {} does not exist", authorId);
      throw new ResourceNotFoundException("User does not exist");
    }
    return blogRepository.findAllByAuthor_UserId(authorId, pageable)
        .stream()
        .map(blog -> modelMapper.map(blog, BlogDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public Blog getBlogDAO(Long blogId) {
    return blogRepository.findById(blogId).orElseThrow(() -> {
      log.error("Blog with id: {} does not exist", blogId);
      return new ResourceNotFoundException("Blog with given id does not exist");
    });
  }

  @Override
  public void deleteBlog(Long blogId) {
    if (!blogRepository.existsById(blogId)) {
      log.error("Blog with id: {} does not exist", blogId);
      throw new ResourceNotFoundException("Blog with given id does not exist");
    }
    blogRepository.deleteById(blogId);
  }
}
