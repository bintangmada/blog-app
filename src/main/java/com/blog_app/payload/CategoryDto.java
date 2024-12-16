package com.blog_app.payload;

public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private int deletedStatus;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, String description, int deletedStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deletedStatus = deletedStatus;
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
}
