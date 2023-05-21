package com.example.bbs.ui.login;

import java.io.Serializable;

public class User implements Serializable {

    private String UserName;//用户名
    private String password;//密码
    private String content;//昵称
    private int image;//头像

    public User(String userName, String password, String content, int image) {
        UserName = userName;
        this.password = password;
        this.content = content;
        this.image = image;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
