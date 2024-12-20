package com.vijay.blog_reactive.service;

import com.vijay.blog_reactive.request.UserRequest;
import com.vijay.blog_reactive.response.UserResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<UserResponse> createUser(UserRequest userRequest);

    CompletableFuture<UserResponse> getUserById(Long id);

    CompletableFuture<List<UserResponse>> getAllUsers();

    CompletableFuture<UserResponse> updateUser(UserRequest userRequest, Long id);

    CompletableFuture<Void> deleteUser(Long id);
}
