package com.vijay.blog_reactive.repository;

import com.vijay.blog_reactive.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
