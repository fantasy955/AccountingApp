package com.experiment.lenovo.accountingsoftware.database;

import android.content.Context;

public class DBManager
{
    /**
     * 单例对象本身
     * */
    private static volatile DBManager dbManager;

    private BillsManager billsManager;

    private UserDataManager userDataManager;

    private BudgetManager budgetManager;

    /**
     * 单例暴露给外部调用
     * */
    public static DBManager getInstance(Context context)
    {
        synchronized (DBManager.class)
        {
            if(dbManager == null)
            {
                dbManager = new DBManager(context);
            }
        }
        return dbManager;
    }

    private DBManager(Context context)
    {
        DatabaseHelpter dbHelper = new DatabaseHelpter(context);
        billsManager = new BillsManager(dbHelper);
        userDataManager = new UserDataManager(dbHelper);
        budgetManager = new BudgetManager(dbHelper);
    }

    /**
     * 账单表
     * */
    public BillsManager getBillsManager()
    {
        return this.billsManager;
    }

    public UserDataManager getUserDataManager(){return this.userDataManager; }

    public BudgetManager getBudgetManager(){return this.budgetManager;}

}
