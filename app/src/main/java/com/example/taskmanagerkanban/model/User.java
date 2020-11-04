package com.example.taskmanagerkanban.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private UUID mUUID;
    private String mUserName;
    private String mPassWord;
    private boolean mIsManager;

    public User(String userName, String passWord,boolean isManager) {
        mUserName = userName;
        mPassWord = passWord;
        mUUID=UUID.randomUUID();
        mIsManager=isManager;
    }
    public User(String userName,String passWord,UUID id,boolean isManager){
        this(userName,passWord,isManager);
        mUUID=id;
    }

    public boolean isManager() {
        return mIsManager;
    }

    public void setManager(boolean manager) {
        mIsManager = manager;
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
