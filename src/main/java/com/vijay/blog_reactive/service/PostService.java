package com.vijay.blog_reactive.service;

import com.vijay.blog_reactive.request.PostRequest;
import com.vijay.blog_reactive.response.PostResponse;
import org.hibernate.query.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PostService {
    CompletableFuture<PostResponse> create(PostRequest postRequest);

    //CompletableFuture<Page<PostResponse>> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    CompletableFuture<PostResponse> getPostById(long id);

    CompletableFuture<PostResponse> updatePost(PostRequest postRequest, long id);

    CompletableFuture<Void> deletePostById(long id);

    CompletableFuture<List<PostResponse>> getPostsByCategory(Long categoryId);


}
