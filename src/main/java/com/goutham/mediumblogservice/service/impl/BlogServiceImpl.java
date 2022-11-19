package com.goutham.mediumblogservice.service.impl;

import com.goutham.mediumblogservice.entity.AppUser;
import com.goutham.mediumblogservice.entity.Blog;
import com.goutham.mediumblogservice.exception.ResourceNotFoundException;
import com.goutham.mediumblogservice.repository.BlogRepository;
import com.goutham.mediumblogservice.service.AppUserService;
import com.goutham.mediumblogservice.service.BlogService;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

  private final BlogRepository blogRepository;
  private final AppUserService appUserService;

  @Override
  public Blog createBlog(Blog blog, Long authorId) {
    AppUser user = appUserService.getUser(authorId);
    blog.setAuthor(user);
    LocalDateTime now = LocalDateTime.now();
    blog.setCreatedAt(now);
    blog.setLastModifiedAt(now);
    return blogRepository.save(blog);
  }

  @Override
  public Blog editBlog(Long blogId, Blog blog) {
    Blog savedBlog = blogRepository.findById(blogId).orElseThrow(() -> {
      log.error("Blog with id: {} does not exist", blogId);
      return new ResourceNotFoundException("Blog with given id does not exist");
    });
    savedBlog.setTitle(blog.getTitle());
    savedBlog.setContent(blog.getContent());
    savedBlog.setLastModifiedAt(LocalDateTime.now());
    return blogRepository.save(savedBlog);
  }

  @Override
  public Blog getBlog(Long blogId) {
    return blogRepository.findById(blogId).orElseThrow(() -> {
      log.error("Blog with id: {} does not exist", blogId);
      return new ResourceNotFoundException("Blog with given id does not exist");
    });
  }

  @Override
  public Page<Blog> getAllBlogs(Pageable pageable) {
    return blogRepository.findAll(pageable);
  }

  @Override
  public Page<Blog> getUserBlogs(Long authorId, Pageable pageable) {
    if (!appUserService.isUserExists(authorId)) {
      log.error("User: {} does not exist", authorId);
      throw new ResourceNotFoundException("User does not exist");
    }
    return blogRepository.findAllByAuthor_Id(authorId, pageable);
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
