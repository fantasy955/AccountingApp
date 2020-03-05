package com.experiment.lenovo.accountingsoftware.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelpter extends SQLiteOpenHelper {
    private static final String DB_NAME = "grjzxt.db"; //数据库名称
    private static final int version = 3; //数据库版本

    public DatabaseHelpter(Context context) {
        super(context, DB_NAME, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BillContact.CREATE_TABLE);
        onUpgrade(db,1,2);
        onUpgrade(db,1,3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion){
            case 2:
                db.execSQL(
                        "CREATE TABLE userinfo"  + "("
                                + "username" + " varchar(20)  not null UNIQUE, "
                                + "password" + " varchar(20) not null, "
                                + "uid INTEGER PRIMARY KEY)"
                );
                break;
            case 3:
                db.execSQL(
                        "CREATE TABLE budgetinfo"  + "("
                                + "value" + " varchar(20)  not null UNIQUE, "
                                + "date" + " DATE DEFAULT CURRENT_DATE not null, "
                                + "uid INTEGER)"
                );
                break;
        }
    }
}
