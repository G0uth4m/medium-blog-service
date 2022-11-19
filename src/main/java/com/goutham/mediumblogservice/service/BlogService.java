package com.goutham.mediumblogservice.service;

import com.goutham.mediumblogservice.entity.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface BlogService {
  Blog createBlog(Blog blog, Long authorId);
  Blog editBlog(Long blogId, Blog blog);
  Blog getBlog(Long blogId);
  Page<Blog> getAllBlogs(Pageable pageable);
  Page<Blog> getUserBlogs(Long authorId, Pageable pageable);
  void deleteBlog(Long blogId);
}
