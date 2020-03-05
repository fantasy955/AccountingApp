package com.experiment.lenovo.accountingsoftware.database;

import android.database.sqlite.SQLiteOpenHelper;

import com.experiment.lenovo.accountingsoftware.common.User;

import java.util.List;

public class UserDataManager {
    private SQLiteOpenHelper sqLiteOpenHelper;

    public UserDataManager(DatabaseHelpter databaseHelpter) {
        this.sqLiteOpenHelper = databaseHelpter;
    }

    public User addUser(User user){
        return UserDataContact.addUser(sqLiteOpenHelper,user);
    }
    public List<User> getAllUser(){
        return UserDataContact.queryAll(sqLiteOpenHelper);
    }
}
