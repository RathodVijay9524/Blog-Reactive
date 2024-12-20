package com.vijay.blog_reactive.request;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserRequest {
    private String name;
    private String username;
    private String email;
    private String password;  // Ensure this field is included
    private Set<Long> roleIds;

}
