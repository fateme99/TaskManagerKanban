package com.example.taskmanagerkanban.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.taskmanagerkanban.database.DatabaseSchema.TaskTable.TaskCols;
import com.example.taskmanagerkanban.model.Task;
import com.example.taskmanagerkanban.repository.TaskRepository;

import androidx.annotation.Nullable;

public class TaskDBHelper extends SQLiteOpenHelper {
    public TaskDBHelper(@Nullable Context context) {
        super(context, DatabaseSchema.NAME, null, DatabaseSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sbTask=new StringBuilder();
        sbTask.append("CREATE TABLE "+ DatabaseSchema.TaskTable.NAME + "( ");
        sbTask.append(TaskCols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , ");
        sbTask.append(TaskCols.TITLE + " TEXT , ");
        sbTask.append(TaskCols.DESCRIPTION + " TEXT , ");
        sbTask.append(TaskCols.TASKSTATE + " TEXT , ");
        sbTask.append(TaskCols.DATE + " TEXT ,");
        sbTask.append(TaskCols.CLOCK + " TEXT ");
        sbTask.append(");");
        sqLiteDatabase.execSQL(sbTask.toString());



        /*StringBuilder sbTaskState=new StringBuilder();
        sbTaskState.append("CREATE TABLE "+ DatabaseSchema.TaskState.NAME+ " ( ");
        sbTaskState.append(TaskStateCols.ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , ");
        sbTaskState.append(TaskStateCols.NAME+" TEXT ");
        sbTaskState.append(" );");
        sqLiteDatabase.execSQL(sbTaskState.toString());*/

        /*StringBuilder sbInsertState1=new StringBuilder();
        sbInsertState1.append("INSERT INTO "+ DatabaseSchema.TaskState.NAME+ " VALUES "+ " ( ");
        sbInsertState1.append("TODO");
        sbInsertState1.append(" );");
        sqLiteDatabase.execSQL(sbInsertState1.toString());

        StringBuilder sbInsertState2=new StringBuilder();
        sbInsertState2.append("INSERT INTO "+ DatabaseSchema.TaskState.NAME+ " VALUES "+ " ( ");
        sbInsertState2.append("DOING");
        sbInsertState2.append(" );");
        sqLiteDatabase.execSQL(sbInsertState2.toString());

        StringBuilder sbInsertState3=new StringBuilder();
        sbInsertState3.append("INSERT INTO "+ DatabaseSchema.TaskState.NAME+ " VALUES "+ " ( ");
        sbInsertState3.append("DONE");
        sbInsertState3.append(" );");
        sqLiteDatabase.execSQL(sbInsertState3.toString());*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
