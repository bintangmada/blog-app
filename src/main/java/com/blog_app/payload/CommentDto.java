package com.blog_app.payload;

public class CommentDto {

    private long id;
    private String name;
    private String email;
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
