package com.blog_app.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentDto {

    private long id;

    // name should not be null or empty
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    // comment body should not be null or empty
    // comment body should have at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Comment body should have at least 10 characters")
    private String body;
    private int deletedStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getDeletedStatus() {
        return deletedStatus;
    }

    public void setDeletedStatus(int deletedStatus) {
        this.deletedStatus = deletedStatus;
    }
}