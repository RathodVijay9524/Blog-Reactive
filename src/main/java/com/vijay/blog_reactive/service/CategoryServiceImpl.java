package com.vijay.blog_reactive.service;

import com.vijay.blog_reactive.entity.Category;
import com.vijay.blog_reactive.exception.ResourceNotFoundException;
import com.vijay.blog_reactive.mapper.Mapper;
import com.vijay.blog_reactive.repository.CategoryRepository;
import com.vijay.blog_reactive.request.CategoryRequest;
import com.vijay.blog_reactive.response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public CompletableFuture<CategoryResponse> createCategory(CategoryRequest categoryRequest) {
        return CompletableFuture.supplyAsync(()->{
            log.info("Creating category: {}", categoryRequest.getName());
            Category category = Mapper.toEntity(categoryRequest, Category.class);
            categoryRepository.save(category);
            return Mapper.toDto(category,CategoryResponse.class);
        });
    }

    @Override
    public CompletableFuture<CategoryResponse> getCategoryById(Long id) {
        return CompletableFuture.supplyAsync(()->{
            log.info("Fetching category with ID: {}", id);
            Category category= categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
           return Mapper.toDto(category,CategoryResponse.class);
        });
    }
    @Override
    public CompletableFuture<List<CategoryResponse>> getAllCategories() {
        return CompletableFuture.supplyAsync(()->{
            log.info("Fetching all categories");
            return categoryRepository.findAll().stream()
                    .map(category -> Mapper.toDto(category,CategoryResponse.class))
                    .collect(Collectors.toList());
        });
    }

    @Override
    public CompletableFuture<CategoryResponse> updateCategory(CategoryRequest categoryRequest, Long id) {
        return CompletableFuture.supplyAsync(()->{

                log.info("Updating category with ID: {}", id);
                Category category = categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
            Category updatedCategory = Mapper.toEntity(categoryRequest, Category.class,"id");
            updatedCategory.setId(id);
            categoryRepository.save(category);
                return Mapper.toDto(updatedCategory, CategoryResponse.class);
            });

    }

    @Override
    public CompletableFuture<Void> deleteCategory(Long id) {
        return CompletableFuture.runAsync(() -> {
            log.info("Deleting category with ID: {}", id);
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
            categoryRepository.delete(category);
        });
    }
}
