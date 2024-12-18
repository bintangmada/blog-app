package com.blog_app.service.impl;

import com.blog_app.entity.Category;
import com.blog_app.entity.Post;
import com.blog_app.exception.ResourceNotFoundException;
import com.blog_app.payload.CategoryDto;
import com.blog_app.payload.PostDto;
import com.blog_app.repository.CategoryRepository;
import com.blog_app.repository.PostRepository;
import com.blog_app.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;

    public CategoryImpl() {
    }

    @Autowired
    public CategoryImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
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

        List<Post> listPost = category.getPosts();

        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

        List<PostDto> listPostDto = listPost
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        categoryDto.setListPostDto(listPostDto);

        return categoryDto;
    }

    // get all categories with post
    @Override
    public List<CategoryDto> getAllCategories(){
        List<Category> listCategories = categoryRepository.findAll();
        List<CategoryDto> listCategoryDto = new ArrayList<>();

        for(Category category : listCategories){
            CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
            List<PostDto> listPostDto = new ArrayList<>();
            for(Post post : category.getPosts()){
                PostDto postDto = modelMapper.map(post, PostDto.class);
                listPostDto.add(postDto);
            }
            categoryDto.setListPostDto(listPostDto);
            listCategoryDto.add(categoryDto);
        }

        return listCategoryDto;
    }

//    @Override
//    public List<CategoryDto> getAllCategories(){
//
//        List<Category> listCategory = categoryRepository.findAll();
//
//        List<CategoryDto> listCategoryDto = listCategory
//                .stream()
//                .map((category) -> {
//                    CategoryDto categoryDtos = modelMapper.map(category, CategoryDto.class);
//
//                    List<PostDto> listPostDto = category
//                            .getPosts()
//                            .stream()
//                            .map(post -> {
//                                PostDto postDto = modelMapper.map(post, PostDto.class);
//                                return postDto;
//                            }).collect(Collectors.toList());
//
//                    categoryDtos.setListPostDto(listPostDto);
//
//                    return categoryDtos;
//                }).collect(Collectors.toList());
//
//        return listCategoryDto;
//    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId){

        // cari category by id
        Category updatedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        updatedCategory.setName(categoryDto.getName());
        updatedCategory.setDescription(categoryDto.getDescription());
        updatedCategory.setDeletedStatus(categoryDto.getDeletedStatus());

        Category savedCategory = categoryRepository.save(updatedCategory);

        return modelMapper.map(savedCategory, CategoryDto.class);

    }

    @Override
    public void deleteCategory(Long categoryId){

        // cari category by id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setDeletedStatus(1);

        categoryRepository.save(category);

    }
}
