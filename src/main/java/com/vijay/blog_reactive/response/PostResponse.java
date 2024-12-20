package com.vijay.blog_reactive.response;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Long categoryId;
    private String categoryName;
    private Long userId;
    private String username;
    private Set<CommentResponse> comments;
}

