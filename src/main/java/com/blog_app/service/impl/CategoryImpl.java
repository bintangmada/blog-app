package com.blog_app.service.impl;

import com.blog_app.entity.Category;
import com.blog_app.exception.ResourceNotFoundException;
import com.blog_app.payload.CategoryDto;
import com.blog_app.repository.CategoryRepository;
import com.blog_app.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryImpl() {
    }

    @Autowired
    public CategoryImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto){
        if(categoryDto == null){
            throw new IllegalArgumentException("CategoryDto is null");
        }

        // convert category dto ke cateogory entity
        Category category = modelMapper.map(categoryDto, Category.class);

        // save category
        Category savedCategory = categoryRepository.save(category);

        // kembalikan category entity ke category dto
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return modelMapper.map(category, CategoryDto.class);
    }
}
