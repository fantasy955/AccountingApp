package com.experiment.lenovo.accountingsoftware.database;

import android.database.sqlite.SQLiteOpenHelper;

import com.experiment.lenovo.accountingsoftware.common.Budget;
import com.experiment.lenovo.accountingsoftware.common.User;

public class BudgetManager {
    private SQLiteOpenHelper sqLiteOpenHelper;

    public BudgetManager(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    public Budget getBudgetByDateAndUid(User user, String year, String month){
        return BudgetContact.getBudgetByDateAndUid(sqLiteOpenHelper,user, year, month);
    }
    public boolean insertBudget(User user,Budget budget){
        return  BudgetContact.insertBudget(sqLiteOpenHelper,user,budget);
    }

    public boolean updateBudget(User user, Budget budget,String year, String month){
        return BudgetContact.updateBudget(sqLiteOpenHelper,user,budget,year,month);
    }
}
