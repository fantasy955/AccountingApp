package com.experiment.lenovo.accountingsoftware.myinfopage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.experiment.lenovo.accountingsoftware.R;
import com.experiment.lenovo.accountingsoftware.common.BillRecord;
import com.experiment.lenovo.accountingsoftware.common.Budget;
import com.experiment.lenovo.accountingsoftware.common.Constants;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.experiment.lenovo.accountingsoftware.database.DBManager;
import com.experiment.lenovo.accountingsoftware.tool.MyDate;

import java.math.BigDecimal;
import java.util.List;

import static com.experiment.lenovo.accountingsoftware.common.Constants.EXPEND;

public class MyInfoFragment  extends Fragment {

    User current_user;
    boolean setBudget; //本月是否设置了预算
    Budget budget;
    private static String year;
    private static String month;
    MyDate myDate; //当前时间 年-月-日
    private String totalExpend; //本月总支出
    TextView textView_budgetStatus;
    TextView textView_budgetLeft;
    TextView textView_totalbudget;
    TextView textView_totalexpend;

    private OnConfirmClickListener mListener;




    public static void setYear(String year) {
        MyInfoFragment.year = year;
    }

    public static void setMonth(String month) {
        MyInfoFragment.month = month;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public static MyInfoFragment newInstance(User user){
        MyInfoFragment myInfoFragment = new MyInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user",user);
        myInfoFragment.setArguments(bundle);
        return myInfoFragment;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        setBudget = false;
        budget = null;
        myDate = new MyDate();
        totalExpend = null;
        final View view = inflater.inflate(R.layout.tab_item_myinfo, container, false);
        TextView textView_budgettitle = (TextView) view.findViewById(R.id.textView_budgettitle);
        textView_budgettitle.setText(myDate.getMonth() + String.valueOf(textView_budgettitle.getText()));

        textView_budgetStatus = (TextView) view.findViewById(R.id.textView_budgetstatus);
        textView_budgetLeft = (TextView) view.findViewById(R.id.textView_budgetleft);
        textView_totalexpend = (TextView) view.findViewById(R.id.textView_totalbudget);
        textView_totalexpend = (TextView) view.findViewById(R.id.textView_totalexpend);
        textView_totalbudget = (TextView) view.findViewById(R.id.textView_totalbudget);
        Log.d("MyInfoFragment","onCreateView");

        if(getArguments() != null){
            final Bundle bundle = getArguments();
            current_user = bundle.getParcelable("user");
            final String year = myDate.getYear();
            final String month = myDate.getMonth();

            //获取当月预算信息，若设置则budget为空
            budget = DBManager.getInstance(inflater.getContext()).getBudgetManager().getBudgetByDateAndUid(current_user,year,month);
            if(budget != null){
                setBudget = true;//标记 ： 本月已经设置了预算
            }
            //获取当月总支出,设置ui界面总支出显示
            List<BillRecord> billRecordList = DBManager.getInstance(inflater.getContext()).getBillsManager().getBillsByDate(myDate.getYear(),myDate.getMonth(),current_user);
            BigDecimal bigDecimal = new BigDecimal("0");
            for(BillRecord billRecord : billRecordList){
                if(billRecord.getBillType().equals(EXPEND)){
                    bigDecimal = bigDecimal.add(new BigDecimal(billRecord.getValues()));
                }
            }
            totalExpend = String.valueOf(bigDecimal.floatValue());
            textView_totalexpend.setText(totalExpend);

            //设置了预算,显示相应信息
            if(setBudget){
                textView_totalbudget.setText(budget.getValuie());
                textView_budgetLeft.setText(String.valueOf(new BigDecimal(budget.getValuie()).subtract(new BigDecimal(totalExpend)).floatValue()));
                switch (new BigDecimal(budget.getValuie()).compareTo(new BigDecimal(totalExpend))){
                    case 0://预算等于总支出
                    case 1://预算大于总支出
                        textView_budgetStatus.setText("正常");
                        textView_budgetStatus.setTextColor(inflater.getContext().getColor(R.color.colorGreen));
                        textView_totalbudget.setTextColor(inflater.getContext().getColor(R.color.colorBlack));
                        textView_budgetLeft.setTextColor(inflater.getContext().getColor(R.color.colorBlack));
                        break;
                    case -1://预算小于总支出
                        textView_budgetStatus.setText("超支");
                        textView_budgetStatus.setTextColor(inflater.getContext().getColor(R.color.colorWarning));
                        textView_totalbudget.setTextColor(inflater.getContext().getColor(R.color.colorWarning));
                        textView_budgetLeft.setTextColor(inflater.getContext().getColor(R.color.colorWarning));
                    break;
                }
            }



            //设置用户名信息
            TextView textView_username = (TextView) view.findViewById(R.id.textView_username);
            textView_username.setText(current_user.getUserName());

            //设置预算按钮添加点击事件
            Button button = (Button) view.findViewById(R.id.button_setbudget);
            button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
//                    new AlertDialog.Builder(inflater.getContext()).setView(R.layout.dialog_inputbudget,R.style.mydialog).show();
                    //弹出设置预算输入框
                    final Dialog dialog = new Dialog(inflater.getContext(),R.style.mydialog);
                    //配置输入框ui界面
                    final View view_dialog_inputbudget = inflater.inflate(R.layout.dialog_inputbudget,container,false);
                    TextView textView_cancel = (TextView) view_dialog_inputbudget.findViewById(R.id.tv_cancel);

                    //输入框取消事件
                    textView_cancel.setOnClickListener(new TextView.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Log.d("textView_cancel","点击取消");
                        }
                    });
                    //输入框确认事件，保存或更新预算信息
                    TextView textView_positive = (TextView) view_dialog_inputbudget.findViewById(R.id.tv_positive);
                    textView_positive.setOnClickListener(new TextView.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            EditText editText_budgetValue = (EditText) view_dialog_inputbudget.findViewById(R.id.editText_budgetvalue);
                            String value = String.valueOf(editText_budgetValue.getText());
                            int intpartNums = 0;
                            int floatNums = 0;
                            if(value.contains(".")){
                                String[] strings = value.split(".");
                                String intPart = strings[0];
                                String floatPart = strings[1];
                                intpartNums = intPart.length();
                                floatNums = floatPart.length();
                            }else {
                                intpartNums = value.length();
                            }
                            if(intpartNums > 9 || floatNums >2){
                                dialog.dismiss();
                                new AlertDialog.Builder(inflater.getContext()).setTitle("提示").setMessage("输入的数字只能包括两位小数并且整数部分不能多余9位").show();
                                return;
                            }

                            if(budget != null){ //本月更改预算信息 更新
                                budget.setValuie(value);
                                budget.setMyDate(new MyDate());
                                budget.setUid(current_user.getUid());
                                DBManager.getInstance(inflater.getContext()).getBudgetManager().updateBudget(current_user,budget,year,month);
                                setBudget = true;
                                update(inflater.getContext());
                                dialog.dismiss();
                                mListener.onConfirmClick(budget);
                                return;
                            }else {//本月第一次设置预算 插入
                                budget = new Budget(value,new MyDate(),current_user.getUid());
                                DBManager.getInstance(inflater.getContext()).getBudgetManager().insertBudget(current_user,budget);
                                Handler handler = new Handler();
                                Message message = new Message();
                                message.what = Constants.UPDATE_BUDGET;
                                handler.sendMessage(message);
                                setBudget = true;
                                update(inflater.getContext());
                                dialog.dismiss();
                                mListener.onConfirmClick(budget);
                                return;
                            }
                        }
                    });
                    dialog.setContentView(view_dialog_inputbudget);
                    dialog.show();
                }
            });
        }else {
            TextView textView_username = (TextView) view.findViewById(R.id.textView_username);
            textView_username.setText("获取当前用户失败");
        }
        return view;
    }


    private void update(Context context){
        if(setBudget){
            textView_totalbudget.setText(budget.getValuie());
            textView_budgetLeft.setText(String.valueOf(new BigDecimal(budget.getValuie()).subtract(new BigDecimal(totalExpend)).floatValue()));
            switch (new BigDecimal(budget.getValuie()).compareTo(new BigDecimal(totalExpend))){
                case 0://预算等于总支出
                case 1://预算大于总支出
                    textView_budgetStatus.setText("正常");
                    textView_budgetStatus.setTextColor(context.getColor(R.color.colorGreen));
                    textView_totalbudget.setTextColor(context.getColor(R.color.colorBlack));
                    textView_budgetLeft.setTextColor(context.getColor(R.color.colorBlack));
                    break;
                case -1://预算小于总支出
                    textView_budgetStatus.setText("超支");
                    textView_budgetStatus.setTextColor(context.getColor(R.color.colorWarning));
                    textView_totalbudget.setTextColor(context.getColor(R.color.colorWarning));
                    textView_budgetLeft.setTextColor(context.getColor(R.color.colorWarning));
                    break;
            }
        }
    }

    /**
     * 确认事件回调
     */
    public interface OnConfirmClickListener {
        public void onConfirmClick(Budget budget);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnConfirmClickListener) getActivity();
    }


}
