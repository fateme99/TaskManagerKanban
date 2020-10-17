package com.example.taskmanagerkanban.model;

import java.util.Date;

public class Task {
    private String mTitle;
    private String mDescribtion;
    private String mDate;
    private String mClock;
    private TaskState mTaskState;

    public Task(String title, String describtion, String date, String clock) {
        mTitle = title;
        mDescribtion = describtion;
        mDate = date;
        mClock = clock;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescribtion() {
        return mDescribtion;
    }

    public void setDescribtion(String describtion) {
        mDescribtion = describtion;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getClock() {
        return mClock;
    }

    public void setClock(String clock) {
        mClock = clock;
    }
}
