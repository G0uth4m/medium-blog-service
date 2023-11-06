package com.goutham.mediumblogservice.service;

import com.goutham.mediumblogservice.dto.blog.BlogCreationDTO;
import com.goutham.mediumblogservice.dto.blog.BlogDTO;
import com.goutham.mediumblogservice.dto.blog.BlogUpdationDTO;
import com.goutham.mediumblogservice.dto.clap.ClapCreationDTO;
import com.goutham.mediumblogservice.entity.Blog;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BlogService {
  BlogDTO createBlog(BlogCreationDTO blogCreationDTO);
  BlogDTO editBlog(Long blogId, BlogUpdationDTO blogUpdationDTO);
  BlogDTO getBlog(Long blogId);
  List<BlogDTO> getAllBlogs(Pageable pageable);
  List<BlogDTO> getUserBlogs(String username, Pageable pageable);
  Blog getBlogDAO(Long blogId);
  void deleteBlog(Long blogId);
  void clapBlog(Long blogId, ClapCreationDTO clapCreationDTO);
  void removeClap(Long blogId, String username);
}
