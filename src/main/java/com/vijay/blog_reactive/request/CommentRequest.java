package com.vijay.blog_reactive.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CommentRequest {
    private String name;
    private String email;
    private String body;
    private Long postId;

}

