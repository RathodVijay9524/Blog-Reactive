package com.vijay.blog_reactive.service;

import com.vijay.blog_reactive.entity.Comment;
import com.vijay.blog_reactive.entity.Post;
import com.vijay.blog_reactive.exception.ResourceNotFoundException;
import com.vijay.blog_reactive.mapper.Mapper;
import com.vijay.blog_reactive.repository.CommentRepository;
import com.vijay.blog_reactive.repository.PostRepository;
import com.vijay.blog_reactive.request.CommentRequest;
import com.vijay.blog_reactive.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class CommentServiceImpl implements CommentService{
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @Override
    public CompletableFuture<CommentResponse> createComment(CommentRequest commentRequest) {
        return CompletableFuture.supplyAsync(()->{
            log.info("Creating comment for post ID: {}", commentRequest.getPostId());
            Post post = postRepository.findById(commentRequest.getPostId())
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentRequest.getPostId()));
            Comment comment= Mapper.toEntity(commentRequest, Comment.class);
            comment.setPost(post);
            commentRepository.save(comment);
            return Mapper.toDto(comment,CommentResponse.class);
        });
    }

    @Override
    public CompletableFuture<CommentResponse> getCommentById(Long id) {
        return CompletableFuture.supplyAsync(()->{
            log.info("Fetching comment with ID: {}", id);
            Comment comment= commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
            return Mapper.toDto(comment,CommentResponse.class);
        });
    }

    @Override
    public CompletableFuture<List<CommentResponse>> getAllComments() {
        return CompletableFuture.supplyAsync(()->{
            log.info("Fetching all comments");
            return commentRepository.findAll().stream()
                    .map(comment -> Mapper.toDto(comment,CommentResponse.class))
                    .collect(Collectors.toList());
        });
    }

    @Override
    public CompletableFuture<CommentResponse> updateComment(CommentRequest commentRequest, Long id) {
        return CompletableFuture.supplyAsync(()->{
            log.info("Updating comment with ID: {}",id);
            Comment comment= commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
            Post post = postRepository.findById(commentRequest.getPostId())
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentRequest.getPostId()));
            Comment updatedComment = Mapper.toEntity(commentRequest, Comment.class,"id");
            updatedComment.setPost(post);
            updatedComment.setId(id);
            commentRepository.save(updatedComment);
            return Mapper.toDto(updatedComment,CommentResponse.class);
        });
    }

    @Override
    public CompletableFuture<Void> deleteComment(Long id) {
        return CompletableFuture.runAsync(()->{
            log.info("Deleting comment with ID: {}", id);
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
            commentRepository.delete(comment);
        });
    }

    @Override
    public CompletableFuture<List<CommentResponse>> getCommentsByPostId(Long postId) {
        return CompletableFuture.supplyAsync(()->{
            log.info("Fetching comments for post ID: {}", postId);
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
            List<Comment> list= commentRepository.findByPostId(postId);
            return list.stream()
                    .map(comment -> Mapper.toDto(comment,CommentResponse.class))
                    .collect(Collectors.toList());
        });
    }
}
