package com.example.taskmanagerkanban.repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerkanban.database.DatabaseSchema;
import com.example.taskmanagerkanban.model.User;
import com.example.taskmanagerkanban.database.DatabaseSchema.UserTable.UserCols;
import java.util.Date;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public User getUser(){
        String userName=getString(getColumnIndex(UserCols.USERNAME));
        String passWord=getString(getColumnIndex(UserCols.PASSWORD));
        UUID uuId= UUID.fromString(getString(getColumnIndex(UserCols.UUID)));
        int isManager_int=getInt(getColumnIndex(UserCols.ISMANAGER));

        Date signUpDate=new Date(getLong(getColumnIndex(UserCols.SIGNUPDATE)));
        boolean isManager=false;
        if (isManager_int==1)
            isManager=true;

        return new User(userName,passWord,uuId,isManager,signUpDate);

    }
}
