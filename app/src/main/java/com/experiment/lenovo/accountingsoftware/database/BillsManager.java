package com.experiment.lenovo.accountingsoftware.database;

import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.experiment.lenovo.accountingsoftware.common.BillRecord;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.experiment.lenovo.accountingsoftware.tool.MyDate;

import java.util.List;

public class BillsManager {
    private SQLiteOpenHelper dbHelper;
    public BillsManager(DatabaseHelpter databaseHelpter) {
        dbHelper = databaseHelpter;
    }
    /**
     * 更新某年某月
     * */
    public List<BillRecord> getBillsByDate(MyDate date){
        return BillContact.getBillsByDate(dbHelper,date);
    }
    public List<BillRecord> getBillsByDate(String year, String month,User user){
        return BillContact.getBillsByDate(dbHelper,year,month,user);
    }
    /**
     * 获取所有记录
     * */
    public List<BillRecord> getAllBills(User user){
        return BillContact.query(dbHelper,user);
    }
    /**
     * 更新某条记录
     * */
    public void update(BillRecord billRecord){
        if(billRecord == null)
        {
            return;
        }
        BillContact.update(dbHelper, billRecord);
    }
    /**
     * 删除某条记录
     * */
    public void delete(String billID)
    {
        if(TextUtils.isEmpty(billID))
        {
            return;
        }
        BillContact.delete(dbHelper, billID);
    }

    /**
     * 插入一条数据
     * */
    public void insert(BillRecord billRecord,User user)
    {
        if(billRecord == null)
        {
            return;
        }
        BillContact.insert(dbHelper, billRecord,user);
    }
    /**
     * 清空表
     * */
    public void clear()
    {
        BillContact.clear(dbHelper);
    }
}
