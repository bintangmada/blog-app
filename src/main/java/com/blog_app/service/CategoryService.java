package com.blog_app.service;

import com.blog_app.payload.CategoryDto;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId);
}
