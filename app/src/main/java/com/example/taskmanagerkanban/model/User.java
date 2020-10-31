package com.example.taskmanagerkanban.model;

import java.util.UUID;

public class User {
    private UUID mUUID;
    private String mUserName;
    private String mPassWord;

    public User(String userName, String passWord) {
        mUserName = userName;
        mPassWord = passWord;
        mUUID=UUID.randomUUID();
    }
    public User(String userName,String passWord,UUID id){
        this(userName,passWord);
        mUUID=id;
    }

    public UUID getUUID() {
        return mUUID;
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
