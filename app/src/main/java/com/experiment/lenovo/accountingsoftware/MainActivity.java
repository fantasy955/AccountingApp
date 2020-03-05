package com.experiment.lenovo.accountingsoftware;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.experiment.lenovo.accountingsoftware.accuntingpage.AccountingActivity;
import com.experiment.lenovo.accountingsoftware.common.BillRecord;
import com.experiment.lenovo.accountingsoftware.common.Budget;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.experiment.lenovo.accountingsoftware.database.DBManager;
import com.experiment.lenovo.accountingsoftware.detailpage.DetailFragment;
import com.experiment.lenovo.accountingsoftware.myinfopage.MyInfoFragment;
import com.experiment.lenovo.accountingsoftware.tool.MyDate;
import com.experiment.lenovo.accountingsoftware.tool.MyTime;

import java.util.ArrayList;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity implements MyInfoFragment.OnConfirmClickListener{
    private DetailFragment detailFragment;
    private MyInfoFragment myInfoFragment;
    private boolean onDetailFragment;
    private boolean onMyInfoFragment;

    private List<BillRecord> billRecordList;
    private static User current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        iniData();
        setContentView(R.layout.activity_main);
        onDetailFragment = false;
        onMyInfoFragment = false;

        Intent intent=getIntent();
        current_user=intent.getParcelableExtra("user");
        Log.d("MainActivity",current_user.toString());

        SQLiteStudioService.instance().start(this);  //数据库管理工具支持

    }

    @Override
    protected void onStart() {
        super.onStart();
        initFragments();
        change2DetailFragment();
        Log.e("On MainActivity","onStart");
    }

    public void onClickButton_detail(View view){
        change2DetailFragment();
        Log.e("点击","点击了明细");
    }
    public void onClickButton_accounting(View view){
        Intent intent = new Intent(MainActivity.this,AccountingActivity.class);
        intent.putExtra("user",current_user);
        startActivity(intent);
    }
    public void onClickButton_myinfo(View view){
       change2MyInfoFragment();
        Log.e("点击","点击了我的");
    }


    public void initDetailBills(){
        billRecordList = new ArrayList<>();
        MyTime myTime = new MyTime();
        billRecordList = DBManager.getInstance(MainActivity.this).getBillsManager().getBillsByDate(myTime.getYear(),myTime.getMonth(),current_user);
        if(billRecordList == null || billRecordList.size()==0){

        }else{
            for(BillRecord billRecord : billRecordList){

            }
        }
    }


    private List<BillRecord> test_initBills(){
        List<BillRecord> billRecords = new ArrayList<>();
        for(int i=1 ; i<=30 ; i++){
            billRecords.add(new BillRecord(
                    "0","餐饮","100","测试",
                    new MyDate("2019","09",String.valueOf(i)),
                    new MyTime(),
                            String.valueOf(i)
                    )
            );
        }
        return billRecords;
    }

    public void initFragments(){
//        List<BillRecord> billRecords = new ArrayList<>();
//        billRecords = test_initBills();
//        billRecords = DBManager.getInstance(MainActivity.this).getBillsManager().getAllBills(current_user);
//        Log.e("结束初始化","结束初始化账单数据");

        detailFragment = DetailFragment.newInstance(current_user);
//        detailFragment = new DetailFragment();

        myInfoFragment = MyInfoFragment.newInstance(current_user);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.add(R.id.frameLayout,myInfoFragment);
        transaction.add(R.id.frameLayout,detailFragment);

        transaction.hide(myInfoFragment);
        transaction.hide(detailFragment).commit();
    }

    public void change2DetailFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        if(onMyInfoFragment == true){
            transaction.hide(myInfoFragment);
        }
        transaction.show(detailFragment).commit();
        onDetailFragment = true;
        onMyInfoFragment = false;
    }
    public void change2MyInfoFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        if(onDetailFragment == true){
            transaction.hide(detailFragment);
        }
        transaction.show(myInfoFragment).commit();
        onDetailFragment = false;
        onMyInfoFragment = true;
    }



    @Override
    public void onConfirmClick(Budget budget) {
        Log.d("MainActivity","onConfirmClick");
        detailFragment.update(budget);
    }
}
