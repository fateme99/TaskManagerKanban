package com.example.taskmanagerkanban.model;

import java.util.Date;

public class Task {
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private String mClock;
    private String mTaskState;
    //private TaskState mTaskState;

    public Task(String title, String description, Date date, String clock, String taskState) {
        mTitle = title;
        mDescription = description;
        mDate = date;
        mClock = clock;
        mTaskState = taskState;
    }

    public String getTaskState() {
        return mTaskState;
    }

    public void setTaskState(String taskState) {
        mTaskState = taskState;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getClock() {
        return mClock;
    }

    public void setClock(String clock) {
        mClock = clock;
    }
}
