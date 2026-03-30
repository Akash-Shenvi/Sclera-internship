package com.sclera.blog.mapper;

import com.sclera.blog.dto.response.UserSearchResponse;
import com.sclera.blog.dto.response.UserResponse;
import com.sclera.blog.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);

    UserSearchResponse toSearchDto(User user);
}
