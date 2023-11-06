package com.goutham.mediumblogservice.config;

import com.goutham.mediumblogservice.dto.blog.BlogDTO;
import com.goutham.mediumblogservice.entity.Blog;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    TypeMap<Blog, BlogDTO> typeMap = mapper.createTypeMap(Blog.class, BlogDTO.class);
    typeMap.addMapping(blog -> blog.getAuthor().getUsername(), BlogDTO::setAuthor);
    typeMap.addMapping(blog -> blog.getClaps().size(), BlogDTO::setClaps);
    return mapper;
  }
}
