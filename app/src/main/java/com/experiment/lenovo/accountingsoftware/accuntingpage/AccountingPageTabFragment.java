package com.experiment.lenovo.accountingsoftware.accuntingpage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.experiment.lenovo.accountingsoftware.R;
import com.experiment.lenovo.accountingsoftware.common.BillRecord;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.experiment.lenovo.accountingsoftware.database.DBManager;
import com.experiment.lenovo.accountingsoftware.tool.MyDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.experiment.lenovo.accountingsoftware.common.Constants.billClassImages;
import static com.experiment.lenovo.accountingsoftware.common.Constants.billClassNames;
import static com.experiment.lenovo.accountingsoftware.common.Constants.incomeClassImages;
import static com.experiment.lenovo.accountingsoftware.common.Constants.incomeClassNames;

public class AccountingPageTabFragment extends Fragment {
    private TextView textView_billvalue;
    private EditText editText_billnote;
    private List<ExpendFragmentRecycleViewAdapterItem> expendFragmentRecycleViewAdapterItems;
    private List<IncomeFragmentRecycleViewAdapterItem> incomeFragmentRecycleViewAdapterItems;
    private String billNote;
    private User user;
    private MyDate selectedDate;
    private MyDate today;

    TextView textView_0;
    TextView textView_1;
    TextView textView_2;
    TextView textView_3;
    TextView textView_4;
    TextView textView_5;
    TextView textView_6;
    TextView textView_7;
    TextView textView_8;
    TextView textView_9;
    TextView textView_point;
    TextView textView_del;
    TextView textView_finish;
    TextView textView_setdate;

    public static void setBillType(String billType) {
        AccountingPageTabFragment.billType = billType;
    }

    private static String billType;
    public static void setBillClass(String billClass) {
        AccountingPageTabFragment.billClass = billClass;
    }

    private static String billClass;
    private MyDate date0fBill;
    private String billValue;
    private TableLayout tableLayout_0;


    private int selection = 0;
    public AccountingPageTabFragment(){

    }
    @SuppressLint("ValidFragment")
    public AccountingPageTabFragment(int selection,User user){
        this.selection = selection;
        this.user = user;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        billType = "0";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        selectedDate = new MyDate();
        today = new MyDate();

        Context context = inflater.getContext();
        View view = inflater.inflate(R.layout.accountingpage_tabitem_expendpage, null);

        switch (selection){
            case 0:

                break;
            case 1:
                billType = "1";
                view = inflater.inflate(R.layout.accountingpage_tabitem_incomepage, null);
                tableLayout_0 = (TableLayout) view.findViewById(R.id.tableLayout_incomeinput);
                textView_0 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_0);
                textView_1 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_1);
                textView_2 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_2);
                textView_3 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_3);
                textView_4 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_4);
                textView_5 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_5);
                textView_6 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_6);
                textView_7 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_7);
                textView_8 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_8);
                textView_9 = (TextView) view.findViewById(R.id.incomepage_billvalue_input_9);
                textView_point = (TextView) view.findViewById(R.id.incomepage_billvalue_input_point);
                textView_del = (TextView) view.findViewById(R.id.incomepage_billvalue_input_del);
                textView_finish = (TextView) view.findViewById(R.id.incomepage_billvalue_input_finish);
                textView_setdate = (TextView) view.findViewById(R.id.incomepage_billvalue_input_setdate);

                textView_billvalue = (TextView) view.findViewById(R.id.incomepage_billvalue);
                editText_billnote = (EditText) view.findViewById(R.id.editText_incomebillnote);

                addNumListener(textView_0,"0");
                addNumListener(textView_1,"1");
                addNumListener(textView_2,"2");
                addNumListener(textView_3,"3");
                addNumListener(textView_4,"4");
                addNumListener(textView_5,"5");
                addNumListener(textView_6,"6");
                addNumListener(textView_7,"7");
                addNumListener(textView_8,"8");
                addNumListener(textView_9,"9");
                addNumListener(textView_point,".");
                addDelListener(textView_del);
                addFinishListener(textView_finish);
                addSelectDateListener(textView_setdate,inflater,container);

                initIncomeFragmentRecycleViewAdapterItems();
                IncomeFragmentRecycleViewAdapter incomeFragmentRecycleViewAdapter = new IncomeFragmentRecycleViewAdapter(incomeFragmentRecycleViewAdapterItems,tableLayout_0);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_billincomeclass);
                //创建一个包含3列的网格布局管理器对象
                GridLayoutManager gridLayoutManager = new GridLayoutManager(inflater.getContext(),4);
                recyclerView.setLayoutManager(gridLayoutManager);
                //使用适配器对象adapter为recyclerView加载数据
                recyclerView.setAdapter(incomeFragmentRecycleViewAdapter);
                return view;
        }

        tableLayout_0 = (TableLayout) view.findViewById(R.id.tableLayout_expendinput);
        textView_0 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_0);
        textView_1 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_1);
        textView_2 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_2);
        textView_3 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_3);
        textView_4 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_4);
        textView_5 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_5);
        textView_6 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_6);
        textView_7 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_7);
        textView_8 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_8);
        textView_9 = (TextView) view.findViewById(R.id.expendpage_billvalue_input_9);
        textView_point = (TextView) view.findViewById(R.id.expendpage_billvalue_input_point);
        textView_del = (TextView) view.findViewById(R.id.expendpage_billvalue_input_del);
        textView_finish = (TextView) view.findViewById(R.id.expendpage_billvalue_input_finish);
        textView_setdate = (TextView) view.findViewById(R.id.expendpage_billvalue_input_setdate);

        textView_billvalue = (TextView) view.findViewById(R.id.expendpage_billvalue);
        editText_billnote = (EditText) view.findViewById(R.id.editText_billnote);


        addNumListener(textView_0,"0");
        addNumListener(textView_1,"1");
        addNumListener(textView_2,"2");
        addNumListener(textView_3,"3");
        addNumListener(textView_4,"4");
        addNumListener(textView_5,"5");
        addNumListener(textView_6,"6");
        addNumListener(textView_7,"7");
        addNumListener(textView_8,"8");
        addNumListener(textView_9,"9");
        addNumListener(textView_point,".");
        addDelListener(textView_del);
        addFinishListener(textView_finish);
        addSelectDateListener(textView_setdate,inflater,container);

        initExpendFragmentRecycleViewAdapterItems();
        ExpendFragmentRecycleViewAdapter expendFragmentRecycleViewAdapter = new ExpendFragmentRecycleViewAdapter(expendFragmentRecycleViewAdapterItems,tableLayout_0);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_billexpendclass);
        //创建一个包含3列的网格布局管理器对象
        GridLayoutManager gridLayoutManager = new GridLayoutManager(inflater.getContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        //使用适配器对象adapter为recyclerView加载数据
        recyclerView.setAdapter(expendFragmentRecycleViewAdapter);
        return view;
    }

    private void initIncomeFragmentRecycleViewAdapterItems() {
         incomeFragmentRecycleViewAdapterItems = new ArrayList<IncomeFragmentRecycleViewAdapterItem>();
        for(int i =0;i<incomeClassImages.length;i++){
            incomeFragmentRecycleViewAdapterItems.add(
                    new IncomeFragmentRecycleViewAdapterItem(incomeClassNames[i],incomeClassImages[i])
            );
        }
    }

    protected void initExpendFragmentRecycleViewAdapterItems(){
        expendFragmentRecycleViewAdapterItems = new ArrayList<ExpendFragmentRecycleViewAdapterItem>();
        for(int i =0;i<billClassImages.length;i++){
            expendFragmentRecycleViewAdapterItems.add(
                    new ExpendFragmentRecycleViewAdapterItem(billClassNames[i],billClassImages[i])
            );
        }

    }
    private void addNumListener(final TextView textView, final String num){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("textView_1","点击了"+num);
                String value = (String) textView_billvalue.getText();
                if(value.length()>=11){
                    return;
                }
                if(value.equals("0.00") || value.equals("0")){
                    textView_billvalue.setText(num);
                    billValue = (String) textView_billvalue.getText();
                    return;
                }else if(value.contains(".")) {
                    if(num.equals("."))//再次输入小数点
                        return;
                    if(value.indexOf(".")==0){//第一个数为小数点
                        //第一个数为小数点
                        Log.e("value.indexOf(\".\")==0","第一个数为小数点");
                        if(value.length() >= 2){
                            //小数点后有数字
                            String temp = new String(value.getBytes(),1,value.length()-1);
                            Log.e("小数部分",temp);
                            if(temp.length() == 2) {
                                Log.e("TAG","小数部分有两位了");
                                return;
                            }
                            else {
                                textView_billvalue.setText(value + num);
                                billValue = (String) textView_billvalue.getText();
                                return;
                            }
                        }
                        else {//小数点后没有数字 直接添加
                            textView_billvalue.setText(value + num);
                            billValue = (String) textView_billvalue.getText();
                            return;
                        }
                    }
                    if(value.indexOf(".")!=0){//有整数部分
                        if(value.indexOf(".") == value.length()-1){
                            //最后一位是小数点
                            Log.e("最后一位是小数点","yes");
                            textView_billvalue.setText(value + num);
                            billValue = (String) textView_billvalue.getText();
                            return;
                        }else{
                            //小数点在中间
                            Log.e("小数点在中间",value);
                            String intPart = new String(value.getBytes(),0,value.indexOf("."));
                            String folatPart = new String(value.getBytes(),value.indexOf(".") +1,value.length() - value.indexOf(".") -1);
                            Log.e("整数部分",intPart);
                            Log.e("小数部分",folatPart);
                            if(folatPart.length() < 2){
                                textView_billvalue.setText(value + num);
                                billValue = (String) textView_billvalue.getText();
                                return;
                            }else
                                return;
                        }
                    }
                }else {
                    if(value.length() == 8 && !num.equals(".")){  //不包含小数点且位数为8,且输入不是小数点
                        return;
                    }else if(num.equals(".")){  //输入为小数点，并且当前不含小数点
                        textView_billvalue.setText(value + num);
                        billValue = (String) textView_billvalue.getText();
                        return;
                    } else {
                        textView_billvalue.setText(value + num);
                        billValue = (String) textView_billvalue.getText();
                        return;
                    }
                }
            }
        });
    }


    private void addDelListener(final TextView textView){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","点击了删除");
                String value = (String) textView_billvalue.getText();
                if(value.equals("0.00") || value.equals(0))
                    return;
                if(value.length() == 1){
                    textView_billvalue.setText("0");
                } else {
                  textView_billvalue.setText(new String(value.getBytes(),0,value.length()-1));
                }
            }
        });
    }

    public void addFinishListener(TextView textView){
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("TAG","点击了确认");
                String note= String.valueOf(editText_billnote.getText());
                String value = (String) textView_billvalue.getText();
                Log.e("备注",note);
                Log.e("金额",String.valueOf(parseNumStr(value)));
                if(parseNumStr(value) == 0){
                    Toast.makeText(v.getContext(), "请输入正确的金额!", Toast.LENGTH_LONG).show();
                    Log.e("请输入正确的金额","请输入正确的金额");
                    return;
                }
                if(note.length() == 0) {
                    note = billClass;
                }
                BillRecord billRecord = new BillRecord(billType,billClass,billValue,note);
                billRecord.setDateOfBill(selectedDate);
                DBManager.getInstance(v.getContext()).getBillsManager().insert(billRecord,user);
                getActivity().finish();
            }
        }
        );
    }
    public void addSelectDateListener(final TextView textView, final LayoutInflater inflater, final ViewGroup container){
        textView.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {
                final ArrayAdapter<String> arrayAdapter_year = new ArrayAdapter<String>(inflater.getContext(),R.layout.spinner_item);
                final ArrayAdapter<String> arrayAdapter_month = new ArrayAdapter<String>(inflater.getContext(),R.layout.spinner_item);
                final ArrayAdapter<String> arrayAdapter_day = new ArrayAdapter<String>(inflater.getContext(),R.layout.spinner_item);
                arrayAdapter_year.add("年");
                arrayAdapter_month.add("月");
                arrayAdapter_day.add("日");
                initSpinnerAdapterYear(arrayAdapter_year);

                //弹出选择日期输入框
                final Dialog dialog = new Dialog(inflater.getContext(),R.style.mydialog);
                //配置输入框ui界面
                final View view_dialog_inputbudget = inflater.inflate(R.layout.dialog_inputdate,container,false) ;
                final Spinner spinner_year = (Spinner) view_dialog_inputbudget.findViewById(R.id.spinner_year_selected_adding);
                spinner_year.setAdapter(arrayAdapter_year);
                spinner_year.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position != 0){
                            initSpinnerAdapterMonth(arrayAdapter_month);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                final Spinner spinner_month = (Spinner) view_dialog_inputbudget.findViewById(R.id.spinner_month_selected_adding);
                spinner_month.setAdapter(arrayAdapter_month);
                spinner_month.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position != 0){
                            int indexOfYear = spinner_year.getSelectedItemPosition();
                            String selectedYear = arrayAdapter_year.getItem(indexOfYear);
                            int indexOfMonth = spinner_month.getSelectedItemPosition();
                            String selectedMonth = arrayAdapter_month.getItem(indexOfMonth);
                            initSpinnerAdapterDay(selectedYear, selectedMonth,arrayAdapter_day);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                final Spinner spinner_day = (Spinner) view_dialog_inputbudget.findViewById(R.id.spinner_day_selected_adding);
                spinner_day.setAdapter(arrayAdapter_day);

                TextView textView_cancel = (TextView) view_dialog_inputbudget.findViewById(R.id.tv_cancel_dateselect);
                //输入框取消事件
                textView_cancel.setOnClickListener(new TextView.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Log.d("textView_cancel","点击取消");
                    }
                });
                //输入框确认事件，保存或更新预算信息
                TextView textView_positive = (TextView) view_dialog_inputbudget.findViewById(R.id.tv_positive_dateselect);
                textView_positive.setOnClickListener(new TextView.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        int indexOfYear = spinner_year.getSelectedItemPosition();
                        int indexOfMonth = spinner_month.getSelectedItemPosition();
                        int indexOfDay = spinner_day.getSelectedItemPosition();
                        Log.d("选择日期",String.valueOf(indexOfYear) + "-" + String.valueOf(indexOfMonth) + "-" +String.valueOf(indexOfDay) );
                        if(indexOfDay == 0 || indexOfMonth == 0 || indexOfYear == 0){
                            new AlertDialog.Builder(inflater.getContext()).setTitle("提示").setMessage("日期选择失败").show();
                        }else {
                            selectedDate.setYear(String.valueOf(Integer.parseInt(today.getYear()) - indexOfYear + 1));
                            selectedDate.setMonth(String.valueOf(indexOfMonth));
                            selectedDate.setDay(String.valueOf(indexOfDay));
                            Log.d("today",today.toStringDate());
                            Log.d("selectedday",selectedDate.toStringDate());

                            if(today.getYear().equals(selectedDate.getYear()) && Integer.parseInt(today.getMonth()) == Integer.parseInt(selectedDate.getMonth()) && Integer.parseInt(today.getDay()) == Integer.parseInt(selectedDate.getDay())){
                                textView_setdate.setText("今天");
                            }else {
                                textView_setdate.setText(selectedDate.toStringDate());
                            }
                        }
                        dialog.dismiss();
                        Log.d("textView_cancel","点击确认");
                    }
                });
                dialog.setContentView(view_dialog_inputbudget);
                dialog.show();
            }
        });

    }

    private float parseNumStr(String string){
        float num = 0;
        if(string.contains(".")) {
            String intPart = new String(string.getBytes(), 0, string.indexOf("."));
            String folatPart = new String(string.getBytes(), string.indexOf(".") + 1, string.length() - string.indexOf(".") - 1);
            for(int i=0;i<intPart.length();i++){
                if(intPart.charAt(i) - '0' >= 0 && intPart.charAt(i) - '0' <= 9 ){
                    num = num * 10 + (intPart.charAt(i) - '0');
                    Log.e("TAG","计算整数"+String.valueOf(num));
                }
            }
            float p = (float) 0.1;
            for(int i=0;i<folatPart.length();i++){
                if(folatPart.charAt(i) - '0' >= 0 && folatPart.charAt(i) - '0' <= 9 ){
                    num =  p * (folatPart.charAt(i) - '0');
                    p *= 0.1;
                    Log.e("TAG","计算小数"+String.valueOf(num));
                }
            }
            return num;
        }
        for(int i=0;i<string.length();i++){
            if(string.charAt(i) - '0' >= 0 && string.charAt(i) - '0' <= 9 ){
                num = num * 10 + (string.charAt(i) - '0');
            }
        }
        return num;
    }

    private void initSpinnerAdapterYear(ArrayAdapter<String> arrayAdapter_year){
        arrayAdapter_year.clear();
        arrayAdapter_year.add("年");
        MyDate current_date = new MyDate();
        for(int i = 0; i<10; i++){  //可选择前十年
            int year = Integer.parseInt(current_date.getYear()) - i;
            arrayAdapter_year.add(String.valueOf(year));
        }
    }

    private void initSpinnerAdapterMonth(ArrayAdapter<String> arrayAdapter_month){
        arrayAdapter_month.clear();
        arrayAdapter_month.add("月");
        MyDate current_date = new MyDate();
        for(int i = 1; i<=12; i++){
            arrayAdapter_month.add(String.valueOf(i));
        }
    }

    private void initSpinnerAdapterDay(String year, String month, ArrayAdapter<String> arrayAdapter_day){
        arrayAdapter_day.clear();
        arrayAdapter_day.add("日");
        MyDate current_date = new MyDate();
        for(int day = 1; day <= Integer.parseInt(MyDate.getEndtOfMonth(year,month)); day++){
            arrayAdapter_day.add(String.valueOf(day));
        }
    }

}
