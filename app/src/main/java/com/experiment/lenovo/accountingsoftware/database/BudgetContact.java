package com.experiment.lenovo.accountingsoftware.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.experiment.lenovo.accountingsoftware.common.Budget;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.experiment.lenovo.accountingsoftware.tool.MyDate;

public class BudgetContact {


    public static Budget getBudgetByDateAndUid(SQLiteOpenHelper sqLiteOpenHelper, User user, String year, String month){
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from budgetinfo where date between ? and ? and uid=? ",
                new String[]{year+"-"+month+"-0",
                        year + "-" + month + "-" + MyDate.getEndtOfMonth(year,month),
                        user.getUid()
                });
        Budget budget = null;
        while (cursor.moveToNext()){
            budget = getBudgetFromCursor(cursor);
        }
        return budget;
    }

    public static boolean insertBudget(SQLiteOpenHelper sqLiteOpenHelper,User user,Budget budget){
        budget.setUid(user.getUid());
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        long result = db.insert("budgetinfo", null, toContentValues(budget));
        return result ==-1 ? false : true;
    }

    public static boolean updateBudget(SQLiteOpenHelper sqLiteOpenHelper, User user, Budget budget,String year, String month){
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        long result = db.update("budgetinfo", toContentValues(budget), "uid=? and date between ? and ? ", new String[] {
                user.getUid(),
                year+"-"+month+"-0",
                year + "-" + month + "-" + MyDate.getEndtOfMonth(year,month),
        });
        return result ==0 ? false : true;
    }

    /**
     * 将对象保证成ContentValues
     */
    private static ContentValues toContentValues(Budget budget) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", budget.getValuie());
        contentValues.put("date", budget.getMyDate().toString());
        contentValues.put("uid",budget.getUid());
        return contentValues;
    }

    /**
     * 将账单对象从Cursor中取出
     */
    private static Budget getBudgetFromCursor(Cursor cursor) {
        String value = cursor.getString(cursor.getColumnIndex("value"));
        MyDate myDate = new MyDate(cursor.getString(cursor.getColumnIndex("date")));
        String uid = cursor.getString(cursor.getColumnIndex("uid"));

        Budget budget = new Budget(
                value,
                myDate,
                uid
        );
       return budget;
    }
}