package com.vijay.blog_reactive.request;

import com.vijay.blog_reactive.response.PostResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryRequest {
    private String name;
    private String description;
    private String coverImage;
    @Builder.Default
    private List<PostRequest> posts = new ArrayList<>();
}
