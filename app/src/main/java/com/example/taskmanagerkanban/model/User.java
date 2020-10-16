package com.example.taskmanagerkanban.model;

public class User {
    private String mUserName;
    private String mPassWord;

    public User(String userName, String passWord) {
        mUserName = userName;
        mPassWord = passWord;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String passWord) {
        mPassWord = passWord;
    }
}
