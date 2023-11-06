package com.goutham.mediumblogservice.constants;

public class JpqlQueries {

  public static final String FIND_BLOG_BY_ID = "SELECT new com.goutham.mediumblogservice.dto.blog.BlogDTO(b.blogId, b.title, b.content, b.author.username, COUNT(c), b.createdAt, b.lastModifiedAt) FROM Blog b LEFT JOIN b.claps c WHERE b.blogId = :blogId GROUP by b";
  public static final String FIND_ALL_BLOGS = "SELECT new com.goutham.mediumblogservice.dto.blog.BlogDTO(b.blogId, b.title, b.content, b.author.username, COUNT(c), b.createdAt, b.lastModifiedAt) FROM Blog b LEFT JOIN b.claps c GROUP by b";
  public static final String FIND_ALL_BLOGS_BY_USER = "SELECT new com.goutham.mediumblogservice.dto.blog.BlogDTO(b.blogId, b.title, b.content, b.author.username, COUNT(c), b.createdAt, b.lastModifiedAt) FROM Blog b LEFT JOIN b.claps c WHERE b.author.username = :username GROUP by b";

}
