package com.vijay.blog_reactive.service;

import com.vijay.blog_reactive.request.CategoryRequest;
import com.vijay.blog_reactive.response.CategoryResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CategoryService {
    CompletableFuture<CategoryResponse> createCategory(CategoryRequest categoryRequest);

    CompletableFuture<CategoryResponse> getCategoryById(Long id);

    CompletableFuture<List<CategoryResponse>> getAllCategories();

    CompletableFuture<CategoryResponse> updateCategory(CategoryRequest categoryRequest, Long id);

    CompletableFuture<Void> deleteCategory(Long id);
}
