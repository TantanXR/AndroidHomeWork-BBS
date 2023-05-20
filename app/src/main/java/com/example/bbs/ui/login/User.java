package com.example.bbs.ui.login;

public class User {

    private String UserName;//用户名
    private String Name;//昵称
    private int image;
    private String password;

    public User(String userName, String name, int image, String password) {
        UserName = userName;
        Name = name;
        this.image = image;
        this.password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
