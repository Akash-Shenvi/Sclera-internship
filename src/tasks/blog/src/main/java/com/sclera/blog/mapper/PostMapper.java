package com.sclera.blog.mapper;

import com.sclera.blog.dto.response.PostResponse;
import com.sclera.blog.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PostMapper {

    PostResponse toDto(Post post);
}