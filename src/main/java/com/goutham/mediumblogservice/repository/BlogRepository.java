package com.goutham.mediumblogservice.repository;

import com.goutham.mediumblogservice.constants.JpqlQueries;
import com.goutham.mediumblogservice.dto.blog.BlogDTO;
import com.goutham.mediumblogservice.entity.Blog;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends JpaRepository<Blog, Long> {
  Page<Blog> findAllByAuthor_Username(String username, Pageable pageable);

  @Query(JpqlQueries.FIND_BLOG_BY_ID)
  Optional<BlogDTO> findByBlogId(@Param("blogId") Long blogId);

  @Query(JpqlQueries.FIND_ALL_BLOGS)
  Page<BlogDTO> findAllBlogs(Pageable pageable);

  @Query(JpqlQueries.FIND_ALL_BLOGS_BY_USER)
  Page<BlogDTO> findAllByAuthor(String username, Pageable pageable);
}
