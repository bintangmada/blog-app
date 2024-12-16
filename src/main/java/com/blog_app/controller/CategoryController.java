package com.blog_app.controller;

import com.blog_app.payload.CategoryDto;
import com.blog_app.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id){
        CategoryDto category = categoryService.getCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


}
