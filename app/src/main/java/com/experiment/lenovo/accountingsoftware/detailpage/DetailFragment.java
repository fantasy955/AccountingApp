package com.experiment.lenovo.accountingsoftware.detailpage;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.experiment.lenovo.accountingsoftware.MainActivity;
import com.experiment.lenovo.accountingsoftware.R;
import com.experiment.lenovo.accountingsoftware.common.BillRecord;
import com.experiment.lenovo.accountingsoftware.common.Budget;
import com.experiment.lenovo.accountingsoftware.common.Constants;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.experiment.lenovo.accountingsoftware.database.DBManager;
import com.experiment.lenovo.accountingsoftware.myinfopage.MyInfoFragment;
import com.experiment.lenovo.accountingsoftware.tool.MyDate;
import com.experiment.lenovo.accountingsoftware.tool.MyTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.experiment.lenovo.accountingsoftware.common.Constants.EXPEND;
import static com.experiment.lenovo.accountingsoftware.common.Constants.INCOME;
import static com.experiment.lenovo.accountingsoftware.common.Constants.PARAM_BILLRECORDS;

public class DetailFragment  extends Fragment {

    private List<BillRecord> billRecords;
    private List<String> yearList;
    private List<String> monthList;
    private User current_user;
    TextView textView_expend;
    TextView textView_income;
    RecyclerView recyclerView;
    boolean setBudget;
    Budget budget;
    MyDate today;
    String year_selected;
    String month_selected;
    String current_totalExpend;
    private boolean firstIn;
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public static DetailFragment newInstance(User user){
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user",user);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        firstIn = true;
        setBudget = false;
        budget = null;
        today = new MyDate();
        billRecords = new ArrayList<>();
        yearList = new ArrayList<String>();
        monthList = new ArrayList<String>();

        View view = inflater.inflate(R.layout.tab_item_detail, container, false);

        textView_expend = (TextView) view.findViewById(R.id.textView_expend);
        textView_income = (TextView) view.findViewById(R.id.textView_income);
        textView_income.setText(Constants.STRING_INCOME + ":"+"0");
        textView_expend.setText(Constants.STRING_EXPEND + ":"+"0");

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            current_user = bundle.getParcelable("user");

            ArrayAdapter<String> arrayAdapter_year = new ArrayAdapter<String>(inflater.getContext(), R.layout.spinner_item);
            ArrayAdapter<String> arrayAdapter_month = new ArrayAdapter<String>(inflater.getContext(), R.layout.spinner_item);

            initArrayAdapterMonthInfo(arrayAdapter_month);
            initArrayAdapterYearInfo(arrayAdapter_year);
            final Spinner spinner_year = (Spinner) view.findViewById(R.id.spinner_year);
            spinner_year.setAdapter(arrayAdapter_year);
            final Spinner spinner_month = (Spinner) view.findViewById(R.id.spinner_month);
            spinner_month.setAdapter(arrayAdapter_month);

            recyclerView = (RecyclerView) view.findViewById(R.id.rc_detailPage);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    //当前RecyclerView显示出来的最后一个的item的position
                    int lastPosition = -1;

                    //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof GridLayoutManager) {
                            //通过LayoutManager找到当前显示的最后的item的position
                            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof LinearLayoutManager) {
                            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                            lastPosition = findMax(lastPositions);
                        }

                        //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                        //如果相等则说明已经滑动到最后了
                        if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                            Toast.makeText(inflater.getContext(), "滑动到底了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                }

                private int findMax(int[] lastPositions) {
                    int max = lastPositions[0];
                    for (int value : lastPositions) {
                        if (value > max) {
                            max = value;
                        }
                    }
                    return max;
                }
            });

            Spinner.OnItemSelectedListener spinnerOnClickListener = new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String year = yearList.get(spinner_year.getSelectedItemPosition());
                    String month = monthList.get(spinner_month.getSelectedItemPosition());
                    year_selected = year;
                    month_selected = month;

                    billRecords = DBManager.getInstance(inflater.getContext()).getBillsManager().getBillsByDate(year,month,current_user);
                    budget = DBManager.getInstance(inflater.getContext()).getBudgetManager().getBudgetByDateAndUid(current_user,year,month);

                    HashMap<String,BigDecimal> hashMap = getTotalExpendAndTotalIncome(billRecords);
                    current_totalExpend = String.valueOf(hashMap.get(EXPEND).floatValue());
                    textView_expend.setText(String.valueOf(hashMap.get(EXPEND).floatValue()));
                    if(budget != null && new BigDecimal(budget.getValuie()).compareTo(hashMap.get(EXPEND)) == -1){
                        textView_expend.setTextColor(Color.rgb(221,44,00));
                    }else {
                        textView_expend.setTextColor(textView_income.getCurrentTextColor());
                    }
                    textView_income.setText(String.valueOf(hashMap.get(INCOME).floatValue()));

                    //如果设置了预算 标记超出预算的账单记录
                    billRecords = initBills(String.valueOf(hashMap.get(EXPEND).floatValue()));


                    final DetailPageAdapter detailPageAdapter = new DetailPageAdapter(billRecords);
                    //创建一个包含1列的网格布局管理器对象
                    //使用适配器对象adapter为recyclerView加载数据
                    recyclerView.setAdapter(detailPageAdapter);

                    if(firstIn){
                        firstIn = false;
                    }else {
                        if(billRecords.size() == 0  ){
                            if(firstIn){
                                firstIn = false;
                            }else {
                                new AlertDialog.Builder(inflater.getContext()).setTitle("提示").setMessage("您"+year +"年" + month +"月没有账单记录哦").show();
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };
            spinner_month.setOnItemSelectedListener(spinnerOnClickListener);
            spinner_year.setOnItemSelectedListener(spinnerOnClickListener);
            spinner_month.setSelection(Integer.parseInt(new MyDate().getMonth())-1);

        }
        return view;
    }

    private void initArrayAdapterMonthInfo(ArrayAdapter<String> arrayAdapter){
        for(int i=1 ;i<=12;i++){
            arrayAdapter.add(String.valueOf(i)+"月");
            monthList.add(String.valueOf(i));
        }
    }
    private void initArrayAdapterYearInfo(ArrayAdapter<String> arrayAdapter){
        int year = Integer.parseInt(today.getYear());
        for(int i = 0 ; i<100 ; i++){
            arrayAdapter.add(String.valueOf(year-i)+"年");
            yearList.add(String.valueOf(year-i));
        }
    }
    private HashMap<String,BigDecimal> getTotalExpendAndTotalIncome(List<BillRecord> billRecordList){
        BigDecimal bigDecimalExpend = new BigDecimal("0");
        BigDecimal bigDecimalIncome = new BigDecimal("0");
        HashMap<String,BigDecimal> hashMap = new HashMap<String, BigDecimal>();
        for(BillRecord billRecord : billRecordList){
            if(billRecord.getBillType().equals(EXPEND)){
                bigDecimalExpend = bigDecimalExpend.add(new BigDecimal(billRecord.getValues()));
//                Log.d("计算总支出",String.valueOf(String.valueOf(bigDecimalExpend.floatValue())));
            }else if(billRecord.getBillType().equals(INCOME)){
                bigDecimalIncome = bigDecimalIncome.add(new BigDecimal(billRecord.getValues()));
//                Log.d("收入",billRecord.getValues() + "totalincome:" + String.valueOf(totalIncome));
            }
        }
        hashMap.put(EXPEND,bigDecimalExpend);
        hashMap.put(INCOME,bigDecimalIncome);
        return hashMap;
    }

    private List<BillRecord> initBills(String totalExpend){
        if(budget == null || new BigDecimal(totalExpend).compareTo(new BigDecimal(budget.getValuie())) != 1){ //没有设置预算或者总支出小于或等于预算
            for(BillRecord billRecord : billRecords){
                billRecord.setOutOfBudget(false);
            }
            return billRecords;
        }else {
            BigDecimal bigDecimal_totalExpend = new BigDecimal(totalExpend);
            for(BillRecord billRecord : billRecords){
                if(billRecord.getBillType().equals(EXPEND)){
                    billRecord.setOutOfBudget(true);
                    bigDecimal_totalExpend = bigDecimal_totalExpend.subtract(new BigDecimal(billRecord.getValues()));
                    if(bigDecimal_totalExpend.compareTo(new BigDecimal(budget.getValuie())) != 1){
                        break;
                    }
                }
            }
            return billRecords;
        }
    }

    public void update(Budget budget){
        if(year_selected.equals(today.getYear()) && month_selected.equals(today.getMonth())){
            Log.d("DetailFragment","update");
            this.budget = budget;
            billRecords = initBills(current_totalExpend);
            if(new BigDecimal(current_totalExpend).compareTo(new BigDecimal(budget.getValuie())) != 1){
                textView_expend.setTextColor(textView_income.getCurrentTextColor());
                Log.d("Color()",String.valueOf(textView_income.getCurrentTextColor()));
            }
            final DetailPageAdapter detailPageAdapter = new DetailPageAdapter(billRecords);
            //创建一个包含1列的网格布局管理器对象
            //使用适配器对象adapter为recyclerView加载数据
            recyclerView.setAdapter(detailPageAdapter);
        }
    }

}
