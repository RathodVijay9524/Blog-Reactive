package com.vijay.blog_reactive.service;

import com.vijay.blog_reactive.request.CommentRequest;
import com.vijay.blog_reactive.response.CommentResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CommentService {

        CompletableFuture<CommentResponse> createComment(CommentRequest commentRequest);

        CompletableFuture<CommentResponse> getCommentById(Long id);

        CompletableFuture<List<CommentResponse>> getAllComments();

        CompletableFuture<CommentResponse> updateComment(CommentRequest commentRequest, Long id);

        CompletableFuture<Void> deleteComment(Long id);

        CompletableFuture<List<CommentResponse>> getCommentsByPostId(Long postId);
}
