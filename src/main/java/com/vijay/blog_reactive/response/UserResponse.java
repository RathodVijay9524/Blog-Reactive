package com.vijay.blog_reactive.response;

import com.vijay.blog_reactive.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Set<Role> roles;
}
