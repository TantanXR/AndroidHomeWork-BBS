package com.example.bbs.ui.home;

import java.io.Serializable;

public class Comment implements Serializable {

    private String postTitle;
    private String commentContent;
    private String createTime;
    private String username;

    public Comment(String postTitle, String commentContent, String createTime, String username) {
        this.postTitle = postTitle;
        this.commentContent = commentContent;
        this.createTime = createTime;
        this.username = username;
    }
    public String getPostTitle() {
        return postTitle;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public String getCommentContent() {
        return commentContent;
    }
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
