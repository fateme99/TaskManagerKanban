package com.example.taskmanagerkanban.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.ConsoleMessage;

import com.example.taskmanagerkanban.database.DatabaseSchema;
import com.example.taskmanagerkanban.database.TaskDBHelper;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.database.DatabaseSchema.TaskTable.TaskCols;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class TaskRepository {
    private static TaskRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private TaskRepository(Context context) {
        mContext=context.getApplicationContext();
        TaskDBHelper taskDBHelper=new TaskDBHelper(mContext);
        mDatabase=taskDBHelper.getWritableDatabase();
    }

    public static TaskRepository getInstance(Context context) {
        if (sInstance==null)
            sInstance=new TaskRepository(context);
        return sInstance;
    }


    public List<Task> getTasks(){
        List<Task>tasks=new ArrayList<>();
        TaskCursorWrapper taskCursorWrapper = getTaskCursorWrapper(null, null);
        if (taskCursorWrapper==null || taskCursorWrapper.getCount()==0)
            return tasks;
        try{
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()){
                Task task=taskCursorWrapper.getTask();
                Log.d(TAG, "getTasks: "+task.getTaskState());
                tasks.add(taskCursorWrapper.getTask());
                taskCursorWrapper.moveToNext();
            }
        }finally {
            taskCursorWrapper.close();
        }
        return tasks;
    }
    public List<Task> getTasks(String state){
        List<Task>tasks=new ArrayList<>();
        String selection=TaskCols.TASKSTATE+"=?";
        String[] selectionArgs=new String[]{state};
        TaskCursorWrapper taskCursorWrapper = getTaskCursorWrapper(selection, selectionArgs);
        if (taskCursorWrapper==null || taskCursorWrapper.getCount()==0)
            return tasks;
        try{
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()){
                tasks.add(taskCursorWrapper.getTask());
                taskCursorWrapper.moveToNext();
            }
        }finally {
            taskCursorWrapper.close();
        }
        return tasks;
    }
    public List<Task>getTasks(UUID userId){
        List<Task>tasks=new ArrayList<>();
        String selection=TaskCols.USER_ID+" =? ";
        String[] selectionArgs=new String[]{userId.toString()};
        TaskCursorWrapper taskCursorWrapper=getTaskCursorWrapper(selection,selectionArgs);
        if (taskCursorWrapper==null     ||    taskCursorWrapper.getCount()==0 )
            return tasks;
        try {
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()){
                tasks.add(taskCursorWrapper.getTask());
                taskCursorWrapper.moveToNext();
            }

        }finally {
            taskCursorWrapper.close();
        }
        return tasks;
    }

    public int countTasks(UUID userId){
        return getTasks(userId).size();

    }
    public List<Task> getTasks(String state,UUID uuid){
        List<Task>tasks=new ArrayList<>();
        String selection=TaskCols.USER_ID+" =? "+" AND "+TaskCols.TASKSTATE+ " =? ";
        String[] selectionArgs=new String[]{uuid.toString(),state};
        TaskCursorWrapper taskCursorWrapper=getTaskCursorWrapper(selection,selectionArgs);
        if (taskCursorWrapper==null     ||  taskCursorWrapper.getCount()==0)
            return tasks;
        try {
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()){
                tasks.add(taskCursorWrapper.getTask());
                taskCursorWrapper.moveToNext();
            }

        }finally {
            taskCursorWrapper.close();;
        }
        return tasks;
    }
    public Task getTask(UUID uuid){
        String selection=TaskCols.UUID+" =? ";
        String[] selectionArgs=new String[]{uuid.toString()};
        TaskCursorWrapper taskCursorWrapper=getTaskCursorWrapper(selection,selectionArgs);
        if (taskCursorWrapper==null     ||  taskCursorWrapper.getCount()==0)
            return null;
        try {
            taskCursorWrapper.moveToFirst();
            return taskCursorWrapper.getTask();
        }finally {
            taskCursorWrapper.close();;
        }
    }

    public void updateTask(Task task) {
        String whereClause = TaskCols.UUID + " =? ";
        String[] whereArgs = new String[]{task.getId().toString()};
        mDatabase.update(DatabaseSchema.TaskTable.NAME, getContentValues(task), whereClause, whereArgs);
    }


    public void deleteTask(UUID uuid){
        String whereClause=TaskCols.UUID+" =? ";
        String[] whereArgs=new String[]{uuid.toString()};
        mDatabase.delete(DatabaseSchema.TaskTable.NAME,whereClause,whereArgs);
    }

    public void deleteAll(UUID user_id){
        List<Task>tasks=getTasks(user_id);
        for (Task task :tasks) {
            deleteTask(task.getId());
        }
    }
    public void insert (Task task){
        ContentValues values = getContentValues(task);

        mDatabase.insert(DatabaseSchema.TaskTable.NAME, null, values);

    }

    @NotNull
    private ContentValues getContentValues(Task task) {
        ContentValues values=new ContentValues();
        values.put(TaskCols.UUID,task.getId().toString());
        values.put(TaskCols.TITLE,task.getTitle());
        values.put(TaskCols.DESCRIPTION,task.getDescription());
        values.put(TaskCols.TASKSTATE,task.getTaskState());
        values.put(TaskCols.DATE,task.getDate().getTime());
        values.put(TaskCols.USER_ID,task.getUser_id().toString());
        return values;
    }

    @NotNull
    private TaskCursorWrapper getTaskCursorWrapper(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.TaskTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return new TaskCursorWrapper(cursor);
    }

    public File getPhotoFile(Task task){
        File fileDir=mContext.getFilesDir();
        File file=new File(fileDir,task.getPhotoFileName());
        return file;
    }

}
