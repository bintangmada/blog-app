package com.blog_app.payload;

import com.blog_app.entity.Post;

import java.util.List;

public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private int deletedStatus;
    private List<PostDto> listPostDto;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, String description, int deletedStatus, List<PostDto> listPostDto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deletedStatus = deletedStatus;
        this.listPostDto = listPostDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeletedStatus() {
        return deletedStatus;
    }

    public void setDeletedStatus(int deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public List<PostDto> getListPostDto() {
        return listPostDto;
    }

    public void setListPostDto(List<PostDto> listPostDto) {
        this.listPostDto = listPostDto;
    }
}
