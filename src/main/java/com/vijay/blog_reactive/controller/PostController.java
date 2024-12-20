package com.vijay.blog_reactive.controller;

import com.vijay.blog_reactive.request.PostRequest;
import com.vijay.blog_reactive.response.PostResponse;
import com.vijay.blog_reactive.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @PostMapping
    public CompletableFuture<ResponseEntity<PostResponse>> createPost(@RequestBody PostRequest postRequest) {
        return postService.create(postRequest).thenApply(ResponseEntity::ok);
    }


    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<PostResponse>> getPostById(@PathVariable long id) {
        return postService.getPostById(id).thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<PostResponse>> updatePost(@RequestBody PostRequest postRequest, @PathVariable long id) {
        return postService.updatePost(postRequest, id).thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deletePost(@PathVariable long id) {
        return postService.deletePostById(id).thenApply(aVoid -> ResponseEntity.noContent().build());
    }

    @GetMapping("/category/{categoryId}")
    public CompletableFuture<ResponseEntity<List<PostResponse>>> getPostsByCategory(@PathVariable Long categoryId) {
        return postService.getPostsByCategory(categoryId).thenApply(ResponseEntity::ok);
    }
}
