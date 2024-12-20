package com.vijay.blog_reactive.controller;

import com.vijay.blog_reactive.request.UserRequest;
import com.vijay.blog_reactive.response.UserResponse;
import com.vijay.blog_reactive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public CompletableFuture<ResponseEntity<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserResponse>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<UserResponse>>> getAllUsers() {
        return userService.getAllUsers()
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest, id)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .thenApply(aVoid -> ResponseEntity.noContent().build());
    }
}
