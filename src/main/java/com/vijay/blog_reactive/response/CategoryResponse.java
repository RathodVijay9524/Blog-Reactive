package com.vijay.blog_reactive.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String coverImage;
    private List<PostResponse> posts;
}
