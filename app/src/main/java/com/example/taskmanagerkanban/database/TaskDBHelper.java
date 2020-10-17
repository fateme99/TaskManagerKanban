package com.example.taskmanagerkanban.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.taskmanagerkanban.database.DatabaseSchema.TaskTable.TaskCols;
import androidx.annotation.Nullable;

public class TaskDBHelper extends SQLiteOpenHelper {
    public TaskDBHelper(@Nullable Context context) {
        super(context, DatabaseSchema.NAME, null, DatabaseSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+ DatabaseSchema.TaskTable.NAME + "( ");
        sb.append(TaskCols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , ");
        sb.append(TaskCols.TITLE + " TEXT , ");
        sb.append(TaskCols.DESCRIPTION + " TEXT , ");
        sb.append(TaskCols.DATE + " TEXT ,");
        sb.append(TaskCols.CLOCK + " TEXT ");
        sb.append(");");
        sqLiteDatabase.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
