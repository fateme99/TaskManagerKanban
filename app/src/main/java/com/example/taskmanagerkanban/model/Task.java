package com.example.taskmanagerkanban.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private String mTaskState;
    private UUID mUser_id;
    //private TaskState mTaskState;


    public Task(UUID id, String title, String description, Date date, String taskState , UUID user_id) {
        this(title,description,date,taskState,user_id);
        mId = id;

    }

    public Task(String title, String description, Date date, String taskState,UUID user_id) {
        mTitle = title;
        mDescription = description;
        mDate = date;

        mTaskState = taskState;
        mId=UUID.randomUUID();
        mUser_id=user_id;
    }

    public UUID getUser_id() {
        return mUser_id;
    }

    public void setUser_id(UUID user_id) {
        mUser_id = user_id;
    }

    public Task() {
        mId=UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
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

}
