package com.sclera.blog.mapper;

import com.sclera.blog.dto.response.PostSearchResponse;
import com.sclera.blog.dto.response.PostResponse;
import com.sclera.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PostMapper {

    PostResponse toDto(Post post);

    @Mapping(target = "excerpt", expression = "java(toExcerpt(post.getContent()))")
    PostSearchResponse toSearchDto(Post post);

    default String toExcerpt(String content) {
        if (content == null || content.isBlank()) {
            return "";
        }

        String normalized = content.trim();
        return normalized.length() <= 120
                ? normalized
                : normalized.substring(0, 117) + "...";
    }
}
