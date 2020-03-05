package com.experiment.lenovo.accountingsoftware.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.experiment.lenovo.accountingsoftware.common.BillRecord;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.experiment.lenovo.accountingsoftware.database.bill.BillColumns;
import com.experiment.lenovo.accountingsoftware.tool.MyDate;
import com.experiment.lenovo.accountingsoftware.tool.MyTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;



public class BillContact {
    /**
     * 表名
     * */
    public static final String TABLE_NAME = "bills";

    /**
     * 所有的字段
     * */
    private static final String[] AVAILABLE_PROJECTION = new String[]{
            BillColumns.BILLTYPE,
            BillColumns.BILLCLASS,
            BillColumns.BILLVALUE,
            BillColumns.NOTE,
            BillColumns.DATEOFBILL,
            BillColumns._TIMEOFADDING,
            BillColumns._BID,
            BillColumns.UID
    };


    /**
     * 创建表的语句
     * */
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + BillColumns.BILLTYPE + " INTEGER  not null, "
            + BillColumns.BILLCLASS + " VARCHAR(8) not null, "
            + BillColumns.BILLVALUE + " FLOAT not null, "
            + BillColumns.NOTE + " TEXT DEFAULT null, "
            + BillColumns.DATEOFBILL + " DATE DEFAULT CURRENT_DATE, "
            + BillColumns._TIMEOFADDING + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + BillColumns._BID + " INTEGER PRIMARY KEY, "
            + BillColumns.UID + " INTEGER DEFAULT 1"
            + ")";

    public static final String SQL_SET_LOCAL_TIEMSTAMP = "UPDATE "+TABLE_NAME
            + " SET "+BillColumns._TIMEOFADDING +" = "
            + "datetime(\'now\','localtime') "
            + "where " + BillColumns._BID +" = ";

    /**
     * 删除表的语句
     * */
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * 判断下所请求的字段是否都存在，分支出现操作的字段不存在的错误
     */
    private static void checkColumns(String[] projection)
    {
        if (projection != null)
        {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(AVAILABLE_PROJECTION));
            if (!availableColumns.containsAll(requestedColumns))
            {
//                throw new IllegalArgumentException(TAG + "checkColumns()-> Unknown columns in projection");
            }
        }
    }

    /**
     * 根据日期查询所有记录
     * */
    public static List<BillRecord> getBillsByDate(SQLiteOpenHelper helper, MyDate date)
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, AVAILABLE_PROJECTION, BillColumns.DATEOFBILL + " =? ", new String[] {date.toString()}, null, null, null);
        List<BillRecord> students = new ArrayList<>();
        if (cursor.moveToFirst())
        {
//            Log.d("TAG", "账单存在");
            students.add(getBillFromCursor(cursor));
        }
        else
        {
            Log.d("TAG", "账单不存在");
        }
        cursor.close();
        return students;
    }
    public static List<BillRecord> getBillsByDate(SQLiteOpenHelper helper, String year,String month,User user)
    {
        month = month.length() == 2 ? month : "0" +month;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from bills where dateofbill between ? and ? and uid=? order by date(dateofbill) desc",
                new String[]{year+"-"+month+"-0",
                year + "-" + month + "-" + MyDate.getEndtOfMonth(year,month),
                        user.getUid()
                });
//        Cursor cursor = db.rawQuery("select * from bills where dateofbill between '2019-10-0' and '2019-10-31' and uid =1 order by date(dateofbill) desc",new String[]{});
        List<BillRecord> billRecords = new ArrayList<>();
        while (cursor.moveToNext()){
            billRecords.add(getBillFromCursor(cursor));
//            Log.d("String year",billRecords.get(billRecords.size()-1).toString());
        }
        cursor.close();
        return billRecords;
    }

    /**
     * 查询所有的记录
     * */
    public static List<BillRecord> query(SQLiteOpenHelper helper, User user)
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor  = db.rawQuery("select * from bills where uid=? order by date(dateofbill) desc ",new String[] {user.getUid()});
        List<BillRecord> billRecords = new ArrayList<>();
        while (cursor.moveToNext()) {
            billRecords.add(getBillFromCursor(cursor));
        }
        cursor.close();
        return billRecords;
    }

    /**
    /**
     * 更新账单记录对象
     * */
    public static void update(SQLiteOpenHelper helper, BillRecord billRecord)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABLE_NAME, toContentValues(billRecord), BillColumns._BID + " =? ", new String[] {billRecord.getId()});
    }

    /**
     * 插入新数据
     * */
    public static void insert(SQLiteOpenHelper helper, BillRecord billRecord,User user)
    {
        billRecord.setUid(user.getUid());
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(TABLE_NAME, null, toContentValues(billRecord));
        String sql = "select last_insert_rowid() from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(sql,null);
        String newID = new String();
        if(cursor.moveToFirst()){
            newID = cursor.getString(0);
        }
        Log.e("添加记录",SQL_SET_LOCAL_TIEMSTAMP + newID);
    }


    /**
     * 删除某条记录
     * */
    public static void delete(SQLiteOpenHelper helper, String bid )
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, BillColumns._BID + " =? ", new String[] {bid});
    }

    /**
     * 清空账单表
     */
    public static void clear(SQLiteOpenHelper helper)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    /**
     * 将对象保证成ContentValues
     * */
    private static ContentValues toContentValues(BillRecord billRecord)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BillColumns.BILLTYPE, billRecord.getBillType());
        contentValues.put(BillColumns.BILLCLASS, billRecord.getBillClass());
        contentValues.put(BillColumns.BILLVALUE, billRecord.getValues());
        if(billRecord.isSetNote())
            contentValues.put(BillColumns.NOTE, billRecord.getNote());
        if(billRecord.isSetDate())
            contentValues.put(BillColumns.DATEOFBILL, billRecord.getDateOfBill().toStringDate());
        contentValues.put(BillColumns.UID,billRecord.getUid());
        return contentValues;
    }

    /**
     * 将账单对象从Cursor中取出
     * */
    private static BillRecord getBillFromCursor(Cursor cursor)
    {
        String billType = cursor.getString(cursor.getColumnIndex(BillColumns.BILLTYPE));
        String billClass = cursor.getString(cursor.getColumnIndex(BillColumns.BILLCLASS));
        String value =  cursor.getString(cursor.getColumnIndex(BillColumns.BILLVALUE));
        String note = cursor.getString(cursor.getColumnIndex(BillColumns.NOTE));
        String dateOfBill = cursor.getString(cursor.getColumnIndex(BillColumns.DATEOFBILL));
        String timeOfAdding = cursor.getString(cursor.getColumnIndex(BillColumns._TIMEOFADDING));
        String id = cursor.getString(cursor.getColumnIndex(BillColumns._BID));
        String uid = cursor.getString(cursor.getColumnIndex(BillColumns.UID));


        BillRecord billRecord = new BillRecord(
                billType,
                billClass,
                value,
                note,
                new MyDate(dateOfBill),
                new MyTime(timeOfAdding),
                id
        );
        billRecord.setUid(uid);
//        Log.e("TAGGGGGGGG",billRecord.toString());


        return billRecord;
    }
}
