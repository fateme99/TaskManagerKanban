package com.example.taskmanagerkanban.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerkanban.database.TaskDBHelper;
import com.example.taskmanagerkanban.database.DatabaseSchema;

import com.example.taskmanagerkanban.model.User;
import com.example.taskmanagerkanban.database.DatabaseSchema.UserTable.UserCols;
import com.example.taskmanagerkanban.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository <User>{
    private static UserDBRepository sInstance;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private UserDBRepository(Context context) {
        mContext=context.getApplicationContext();
        TaskDBHelper bookDBHelper=new TaskDBHelper(mContext);
        mDatabase=bookDBHelper.getWritableDatabase();
    }

    public static UserDBRepository getInstance(Context context) {
        if (sInstance==null)
            sInstance=new UserDBRepository(context);
        return sInstance;
    }

    @Override
    public List<User> getList() {
        List<User>users=new ArrayList<>();
        UserCursorWrapper cursor= queryUserCursor(null, null);
        if (cursor ==null || cursor.getCount()==0){
            return users;
        }
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                User user=cursor.getUser();
                users.add(user);
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return users;

    }

    @Override
    public User get(String userName) {
        String where =UserCols.USERNAME+" = ?";
        String[] whereArgs=new String[]{userName};
        UserCursorWrapper cursor= queryUserCursor(where, whereArgs);
        if (cursor ==null || cursor.getCount()==0)
            return null;
        try{
            cursor.moveToFirst();
            User user=cursor.getUser();
            return user;
        }finally {
            cursor.close();
        }

    }



    @Override
    public void insertList(List list) {

    }

    @Override
    public void insert(User user) {
        ContentValues contentValues=getContentValues(user);
        mDatabase.insert(DatabaseSchema.UserTable.NAME,null,contentValues);

    }

    @Override
    public void clear() {

    }

    @Override
    public void delete(User user) {
        String whereClause=UserCols.USERNAME+" = ? ";
        String[] whereArgs=new String[]{user.getUserName()};
        mDatabase.delete(DatabaseSchema.UserTable.NAME,whereClause,whereArgs);
    }

    @Override
    public void update(User user) {
        ContentValues contentValues=getContentValues(user);
        String whereClause=UserCols.USERNAME+" = ? ";
        String[] whereArgs=new String[]{user.getUserName()};
        mDatabase.update(DatabaseSchema.UserTable.NAME,contentValues,whereClause,whereArgs);
    }

    @Override
    public void addList(List list) {

    }
    private ContentValues getContentValues(User user){
        ContentValues contentValues=new ContentValues();
        contentValues.put(UserCols.UUID,user.getUUID().toString());
        contentValues.put(UserCols.USERNAME,user.getUserName());
        contentValues.put(UserCols.PASSWORD,user.getPassWord());
        return contentValues;
    }
    private UserCursorWrapper queryUserCursor(String where, String[] whereArgs) {
        Cursor cursor= mDatabase.query(
                DatabaseSchema.UserTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
        UserCursorWrapper cursorWrapper=new UserCursorWrapper(cursor);
        return cursorWrapper;
    }
}
