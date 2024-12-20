package com.vijay.blog_reactive.service;

import com.vijay.blog_reactive.entity.Category;
import com.vijay.blog_reactive.entity.Post;
import com.vijay.blog_reactive.entity.User;
import com.vijay.blog_reactive.exception.ResourceNotFoundException;
import com.vijay.blog_reactive.mapper.Mapper;
import com.vijay.blog_reactive.repository.CategoryRepository;
import com.vijay.blog_reactive.repository.PostRepository;
import com.vijay.blog_reactive.repository.UserRepository;
import com.vijay.blog_reactive.request.PostRequest;
import com.vijay.blog_reactive.response.PostResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public CompletableFuture<PostResponse> create(PostRequest postRequest) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Creating post: {}", postRequest.getTitle());
            Category category = categoryRepository.findById(postRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postRequest.getCategoryId()));
            User user = userRepository.findById(postRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", postRequest.getUserId()));
            Post post = Post.builder()
                    .title(postRequest.getTitle())
                    .description(postRequest.getDescription())
                    .content(postRequest.getContent())
                    .user(user)
                    .category(category)
                    .build();
            Post savedPost = postRepository.save(post);
            return Mapper.mapToPostResponse(savedPost);
        });
    }

    @Override
    public CompletableFuture<PostResponse> getPostById(long id) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching post with ID: {}", id);
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            return Mapper.mapToPostResponse(post);
        });
    }

    @Override
    public CompletableFuture<PostResponse> updatePost(PostRequest postRequest, long id) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Updating post with ID: {}", id);
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            Category category = categoryRepository.findById(postRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postRequest.getCategoryId()));
            User user = userRepository.findById(postRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", postRequest.getUserId()));

            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setDescription(postRequest.getDescription());
            post.setCategory(category);
            post.setUser(user);

            Post updatedPost = postRepository.save(post);
            return Mapper.mapToPostResponse(updatedPost);
        });
    }

    @Override
    public CompletableFuture<Void> deletePostById(long id) {
        return CompletableFuture.runAsync(() -> {
            log.info("Deleting post with ID: {}", id);
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
            postRepository.delete(post);
        });
    }

    @Override
    public CompletableFuture<List<PostResponse>> getPostsByCategory(Long categoryId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching posts by category ID: {}", categoryId);
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
            List<Post> posts = postRepository.findByCategoryId(categoryId);
            return posts.stream()
                    .map(post -> Mapper.mapToPostResponse(post))
                    .collect(Collectors.toList());
        });
    }


}
