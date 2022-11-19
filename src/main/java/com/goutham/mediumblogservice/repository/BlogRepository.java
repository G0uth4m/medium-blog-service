package com.goutham.mediumblogservice.repository;

import com.goutham.mediumblogservice.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
  Page<Blog> findAllByAuthor_UserId(Long authorId, Pageable pageable);
}
