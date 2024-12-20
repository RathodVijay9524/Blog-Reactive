package com.vijay.blog_reactive.controller;

import com.vijay.blog_reactive.request.CommentRequest;
import com.vijay.blog_reactive.response.CommentResponse;
import com.vijay.blog_reactive.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public CompletableFuture<ResponseEntity<CommentResponse>> createComment(@RequestBody CommentRequest commentRequest) {
        return commentService.createComment(commentRequest)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<CommentResponse>> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<CommentResponse>>> getAllComments() {
        return commentService.getAllComments()
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<CommentResponse>> updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(commentRequest, id)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id)
                .thenApply(aVoid -> ResponseEntity.noContent().build());
    }

    @GetMapping("/post/{postId}")
    public CompletableFuture<ResponseEntity<List<CommentResponse>>> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId)
                .thenApply(ResponseEntity::ok);
    }
}

