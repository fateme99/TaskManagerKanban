package com.example.taskmanagerkanban.repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerkanban.database.DatabaseSchema;
import com.example.taskmanagerkanban.model.User;

import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public User getUser(){
        String userName=getString(getColumnIndex(DatabaseSchema.UserTable.UserCols.USERNAME));
        String passWord=getString(getColumnIndex(DatabaseSchema.UserTable.UserCols.PASSWORD));
        UUID uuId= UUID.fromString(getString(getColumnIndex(DatabaseSchema.UserTable.UserCols.UUID)));
        int isManager_int=getInt(getColumnIndex(DatabaseSchema.UserTable.UserCols.ISMANAGER));
        boolean isManager=false;
        if (isManager_int==1)
            isManager=true;

        return new User(userName,passWord,uuId,isManager);

    }
}
