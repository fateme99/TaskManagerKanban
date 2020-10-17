package com.example.taskmanagerkanban.repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.database.DatabaseSchema.TaskTable.TaskCols;
import com.example.taskmanagerkanban.model.TaskState;

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Task getTask(){
        String title=getString(getColumnIndex(TaskCols.TITLE));
        String desc=getString(getColumnIndex(TaskCols.DESCRIPTION));
        String taskStatestring=getString(getColumnIndex(TaskCols.TASKSTATE));
        String date=getString(getColumnIndex(TaskCols.DATE));
        String clock=getString(getColumnIndex(TaskCols.CLOCK));

        /*if (taskStatestring.equalsIgnoreCase("TO DO"))
            TaskState taskState=TaskState.TODO;
        else if (taskStatestring.equalsIgnoreCase("DOING"))
            taskState=TaskState.DOING;
        else
            taskState=TaskState.DONE;*/
        return new Task(title,desc,date,clock,taskStatestring);
    }
}
