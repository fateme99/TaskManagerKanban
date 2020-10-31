package com.example.taskmanagerkanban.repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.database.DatabaseSchema.TaskTable.TaskCols;
import com.example.taskmanagerkanban.model.TaskState;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Task getTask(){
        UUID uuid=UUID.fromString(getString(getColumnIndex(TaskCols.UUID)));
        String title=getString(getColumnIndex(TaskCols.TITLE));
        String desc=getString(getColumnIndex(TaskCols.DESCRIPTION));
        String taskStatestring=getString(getColumnIndex(TaskCols.TASKSTATE));
        Date date=new Date(getLong(getColumnIndex(TaskCols.DATE)));
        UUID userId=UUID.fromString(getString(getColumnIndex(TaskCols.USER_ID)));

        /*if (taskStatestring.equalsIgnoreCase("TO DO"))
            TaskState taskState=TaskState.TODO;
        else if (taskStatestring.equalsIgnoreCase("DOING"))
            taskState=TaskState.DOING;
        else
            taskState=TaskState.DONE;*/
        return new Task(uuid,title,desc,date,taskStatestring,userId);
    }
}
