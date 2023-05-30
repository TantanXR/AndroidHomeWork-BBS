package com.example.bbs.ui.home;

import java.io.Serializable;

public class Post implements Serializable {

    private String title;
    private String write;
    private String createTime;
    private String content;
    private String recentUpdateTime;

    public Post(String title, String write, String createTime, String content, String recentUpdateTime) {
        this.title = title;
        this.write = write;
        this.createTime = createTime;
        this.content = content;
        this.recentUpdateTime = recentUpdateTime;
    }
    public String getRecentUpdateTime() {
        return recentUpdateTime;
    }
    public void setRecentUpdateTime(String recentUpdateTime) {
        this.recentUpdateTime = recentUpdateTime;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getWrite() {
        return write;
    }
    public void setWrite(String write) {
        this.write = write;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
