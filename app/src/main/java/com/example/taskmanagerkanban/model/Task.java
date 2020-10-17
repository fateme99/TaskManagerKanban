package com.example.taskmanagerkanban.model;

public class Task {
    private String mTitle;
    private String mDescription;
    private String mDate;
    private String mClock;
    private TaskState mTaskState;

    public Task(String title, String description, String date, String clock, TaskState taskState) {
        mTitle = title;
        mDescription = description;
        mDate = date;
        mClock = clock;
        mTaskState = taskState;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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
