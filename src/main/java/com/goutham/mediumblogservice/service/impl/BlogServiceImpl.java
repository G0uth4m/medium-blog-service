package com.goutham.mediumblogservice.service.impl;

import com.goutham.mediumblogservice.dto.blog.BlogCreationDTO;
import com.goutham.mediumblogservice.dto.blog.BlogDTO;
import com.goutham.mediumblogservice.dto.blog.BlogUpdationDTO;
import com.goutham.mediumblogservice.dto.clap.ClapCreationDTO;
import com.goutham.mediumblogservice.entity.AppUser;
import com.goutham.mediumblogservice.entity.Blog;
import com.goutham.mediumblogservice.entity.Clap;
import com.goutham.mediumblogservice.entity.key.ClapKey;
import com.goutham.mediumblogservice.enums.UserRole;
import com.goutham.mediumblogservice.exception.DuplicateResourceException;
import com.goutham.mediumblogservice.exception.ResourceNotFoundException;
import com.goutham.mediumblogservice.repository.BlogRepository;
import com.goutham.mediumblogservice.repository.ClapRepository;
import com.goutham.mediumblogservice.security.util.SecurityContextUtil;
import com.goutham.mediumblogservice.service.AppUserService;
import com.goutham.mediumblogservice.service.BlogService;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

  private ModelMapper modelMapper;
  private final BlogRepository blogRepository;
  private final AppUserService appUserService;
  private final ClapRepository clapRepository;

  @Transactional
  @Override
  public BlogDTO createBlog(BlogCreationDTO blogCreationDTO) {
    AppUser user = appUserService.getUserDAO(SecurityContextUtil.getUsername());
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    Blog blog = Blog.builder()
        .title(blogCreationDTO.getTitle())
        .content(blogCreationDTO.getContent())
        .author(user)
        .claps(Collections.emptySet())
        .createdAt(now)
        .lastModifiedAt(now)
        .build();
    blog = blogRepository.save(blog);
    return modelMapper.map(blog, BlogDTO.class);
  }

  @Transactional
  @Override
  public BlogDTO editBlog(Long blogId, BlogUpdationDTO blogUpdationDTO) {
    Blog blog = getBlogDAO(blogId);
    if (!Objects.equals(blog.getAuthor().getUsername(), SecurityContextUtil.getUsername())) {
      log.error("User: {} is forbidden to edit the blog with id: {}",
          SecurityContextUtil.getUsername(), blog.getBlogId());
      throw new AccessDeniedException("Access is denied");
    }
    blog.setTitle(blogUpdationDTO.getTitle());
    blog.setContent(blogUpdationDTO.getContent());
    blog.setLastModifiedAt(LocalDateTime.now(ZoneOffset.UTC));
    blog = blogRepository.save(blog);
    // TODO: might throw lazy init error for getClaps
    return modelMapper.map(blog, BlogDTO.class);
  }

  @Transactional(readOnly = true)
  @Override
  public BlogDTO getBlog(Long blogId) {
    return blogRepository.findByBlogId(blogId).orElseThrow(() -> {
      log.error("Blog with id: {} does not exist", blogId);
      return new ResourceNotFoundException("Blog with given id does not exist");
    });
  }

  @Transactional(readOnly = true)
  @Override
  public List<BlogDTO> getAllBlogs(Pageable pageable) {
    return blogRepository.findAllBlogs(pageable).toList();
  }

  @Transactional(readOnly = true)
  @Override
  public List<BlogDTO> getUserBlogs(String username, Pageable pageable) {
    if (!appUserService.isUserExists(username)) {
      log.error("User: {} does not exist", username);
      throw new ResourceNotFoundException("User does not exist");
    }
    return blogRepository.findAllByAuthor(username, pageable).toList();
  }

  @Transactional(readOnly = true)
  @Override
  public Blog getBlogDAO(Long blogId) {
    return blogRepository.findById(blogId).orElseThrow(() -> {
      log.error("Blog with id: {} does not exist", blogId);
      return new ResourceNotFoundException("Blog with given id does not exist");
    });
  }

  @Transactional
  @Override
  public void deleteBlog(Long blogId) {
    Blog blog = getBlogDAO(blogId);
    if (!Objects.equals(blog.getAuthor().getUsername(), SecurityContextUtil.getUsername())
        && !SecurityContextUtil.getRoles().contains(UserRole.ROLE_ADMIN)) {
      log.error("User: {} is forbidden to delete blog with id: {}",
          SecurityContextUtil.getUsername(), blog.getBlogId());
      throw new AccessDeniedException("Access is denied");
    }
    blogRepository.deleteById(blogId);
  }

  @Transactional
  @Override
  public void clapBlog(Long blogId, ClapCreationDTO clapCreationDTO) {
    AppUser appUser = appUserService.getUserDAO(clapCreationDTO.getUsername());
    if (!blogRepository.existsById(blogId)) {
      log.error("Blog with id: {} does not exist", blogId);
      throw new ResourceNotFoundException("Blog not found");
    }
    ClapKey clapKey = ClapKey.builder().blogId(blogId).userId(appUser.getUserId()).build();
    if (clapRepository.existsById(clapKey)) {
      log.error("User: {} has already clapped for blog with id: {}", appUser.getUsername(), blogId);
      throw new DuplicateResourceException("User has already clapped for the blog");
    }
    Clap clap = Clap.builder().clapKey(clapKey).createdAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
    clapRepository.save(clap);
  }

  @Transactional
  @Override
  public void removeClap(Long blogId, String username) {
    AppUser appUser = appUserService.getUserDAO(username);
    if (!blogRepository.existsById(blogId)) {
      log.error("Blog with id: {} does not exist", blogId);
      throw new ResourceNotFoundException("Blog not found");
    }
    ClapKey clapKey = ClapKey.builder().blogId(blogId).userId(appUser.getUserId()).build();
    if (!clapRepository.existsById(clapKey)) {
      log.error("User: {}'s clap not found for blog with id: {}", username, blogId);
      throw new ResourceNotFoundException("Clap not found");
    }
    clapRepository.deleteById(clapKey);
  }

}
