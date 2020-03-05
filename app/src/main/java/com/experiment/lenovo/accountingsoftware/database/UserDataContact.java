package com.experiment.lenovo.accountingsoftware.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.experiment.lenovo.accountingsoftware.common.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataContact {
    private static String tableName = "userinfo";


    public static User addUser(SQLiteOpenHelper sqLiteOpenHelper, User newUser){
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        db.insert(tableName,null,toContentValues(newUser));
        String sql = "select last_insert_rowid() from " + tableName ;
        Cursor cursor = db.rawQuery(sql,null);
        String newID = new String();
        if(cursor.moveToFirst()){
            newID = cursor.getString(0);
            newUser.setUid(newID);
        }else {
            newUser = null;
        }
        Log.d("插入","成功"+newID);
        return newUser;
    }
    public static User queryUser(SQLiteOpenHelper sqLiteOpenHelper,String userName){
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from userinfo where username = ?",new String[] {userName});
        boolean flag = false;
        User user = null;
        while (cursor.moveToFirst()){
            flag = true;
            String password = cursor.getString(cursor.getColumnIndex("password"));
            user = new User(userName,password);
        }
        return user;
    }
    public static List<User> queryAll(SQLiteOpenHelper sqLiteOpenHelper){
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor  = db.rawQuery("select * from userinfo",new String[] {});
        List<User> userList = new ArrayList<>();
        while (cursor.moveToNext())
        {
            User user = getUserFromCursor(cursor);
            Log.d("所有信息",user.toString());
            userList.add(user);
        }
        cursor.close();
        return userList;
    }


    /**
     * 将对象保证成ContentValues
     * */
    private static ContentValues toContentValues(User user)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUserName());
        contentValues.put("password", user.getPassword());
        return contentValues;
    }

    /**
     * 将账单对象从Cursor中取出
     * */
    private static User getUserFromCursor(Cursor cursor)
    {
        String name = cursor.getString(cursor.getColumnIndex("username"));
        String paw = cursor.getString(cursor.getColumnIndex("password"));
        String uid = cursor.getString(cursor.getColumnIndex("uid"));

        User user = new User(
                name,
                paw,
                uid
        );
        return user;
    }

}
