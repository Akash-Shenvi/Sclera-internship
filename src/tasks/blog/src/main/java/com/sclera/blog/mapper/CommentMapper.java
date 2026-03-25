package com.sclera.blog.mapper;

import com.sclera.blog.dto.response.CommentResponse;
import com.sclera.blog.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CommentMapper {

    CommentResponse toDto(Comment comment);
}