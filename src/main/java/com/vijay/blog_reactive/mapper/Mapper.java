package com.vijay.blog_reactive.mapper;

import com.vijay.blog_reactive.entity.Category;
import com.vijay.blog_reactive.entity.Comment;
import com.vijay.blog_reactive.entity.Post;
import com.vijay.blog_reactive.entity.User;
import com.vijay.blog_reactive.response.CategoryResponse;
import com.vijay.blog_reactive.response.CommentResponse;
import com.vijay.blog_reactive.response.PostResponse;
import com.vijay.blog_reactive.response.UserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {

    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    public static CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .coverImage(category.getCoverImage())
                .posts(category.getPosts().stream()
                        .map(Mapper::mapToPostResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public static PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .categoryId(post.getCategory().getId())
                .categoryName(post.getCategory().getName())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .comments(post.getComments().stream()
                        .map(Mapper::mapToCommentResponse)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static CommentResponse mapToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .postId(comment.getPost().getId())
                .build();
    }

    public static <S, T> T toDto(S source, Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            System.err.println("Error during mapping to DTO: " + e.getMessage());
            throw new RuntimeException("Error during mapping", e);
        }
    }

    public static <S, T> T toEntity(S source, Class<T> targetClass) {
        return toEntity(source, targetClass, "id");
    }

    public static <S, T> T toEntity(S source, Class<T> targetClass, String... ignoreProperties) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (Exception e) {
            System.err.println("Error during mapping to Entity: " + e.getMessage());
            throw new RuntimeException("Error during mapping", e);
        }
    }
}




