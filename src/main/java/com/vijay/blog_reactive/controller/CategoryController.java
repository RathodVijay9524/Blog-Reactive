package com.vijay.blog_reactive.controller;

import com.vijay.blog_reactive.request.CategoryRequest;
import com.vijay.blog_reactive.response.CategoryResponse;
import com.vijay.blog_reactive.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping
    public CompletableFuture<ResponseEntity<CategoryResponse>> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<CategoryResponse>>> getAllCategories() {
        return categoryService.getAllCategories()
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<CategoryResponse>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(categoryRequest, id)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id)
                .thenApply(aVoid -> ResponseEntity.noContent().build());
    }
}
