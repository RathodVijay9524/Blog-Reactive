package com.vijay.blog_reactive.response;

import com.vijay.blog_reactive.entity.Post;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String name;
    private String email;
    private String body;
    private Long postId;
}


