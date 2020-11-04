package com.example.taskmanagerkanban.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable {
    private UUID mUUID;
    private String mUserName;
    private String mPassWord;
    private boolean mIsManager;
    private Date mDate_signUp;

    public User(String userName, String passWord,boolean isManager) {
        mUserName = userName;
        mPassWord = passWord;
        mUUID=UUID.randomUUID();
        mIsManager=isManager;
        mDate_signUp=new Date();
    }
    public User(String userName,String passWord,UUID id,boolean isManager,Date date_signUp){
        this(userName,passWord,isManager);
        mUUID=id;
        mDate_signUp=date_signUp;
    }

    public Date getDate_signUp() {
        return mDate_signUp;
    }

    public void setDate_signUp(Date date_signUp) {
        mDate_signUp = date_signUp;
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
