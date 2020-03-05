package com.experiment.lenovo.accountingsoftware.detailpage;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.experiment.lenovo.accountingsoftware.R;
import com.experiment.lenovo.accountingsoftware.billinfopage.BillInfoActivity;
import com.experiment.lenovo.accountingsoftware.common.BillRecord;
import java.util.List;

import static com.experiment.lenovo.accountingsoftware.common.Constants.EXPEND;
import static com.experiment.lenovo.accountingsoftware.common.Constants.Equal;
import static com.experiment.lenovo.accountingsoftware.common.Constants.INTENT_PARAM_BILLRECORD;
import static com.experiment.lenovo.accountingsoftware.common.Constants.PARAM_BILLRECORDS;

public class DetailPageAdapter extends RecyclerView.Adapter<DetailPageAdapter.MyViewHolder> {
    private Context context;
    public static boolean[] load ;

    private List<BillRecord> billRecords;


    public DetailPageAdapter(List<BillRecord> billRecords){
        this.billRecords = billRecords;
        load = new boolean[billRecords.size()];
        for(int i=0;i<billRecords.size();i++){
            load[i] = false;
        }
//        Log.e("TAG","进入DetailPageAdapter");
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_tab_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        Log.e("onCreateViewHolder","在onCreateViewHolder内");
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewActivity(context,billRecords.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        BillRecord billRecord = billRecords.get(position);
        String tableName = billRecord.getDateOfBill().getYear() + "年" + billRecord.getDateOfBill().getMonth() + "月" + billRecord.getDateOfBill().getDay() + "日";
        myViewHolder.tableName.setText(tableName);
        myViewHolder.billinfo_class.setText(billRecord.getBillClass());
        if(billRecord.getBillType().equals(EXPEND)){
            myViewHolder.billinfo_value.setText("-"+billRecord.getValues());
        }else{
            myViewHolder.billinfo_value.setText(billRecord.getValues());
        }
        if(billRecord.isOutOfBudget()){
            myViewHolder.billinfo_value.setTextColor(Color.rgb(221,44,00));
            myViewHolder.billinfo_class.setTextColor(Color.rgb(221,44,00));
        }
        if(position >0){
            BillRecord lastBillRecord = billRecords.get(position - 1);
            if(lastBillRecord.getDateOfBill().compare(billRecord.getDateOfBill()) == Equal){
                myViewHolder.tableName.setVisibility(View.GONE);
            }
        }
//        Log.e("onBindViewHolder",String.valueOf(position));
        load[position]  = true;
    }

    @Override
    public int getItemCount() {
//        Log.e("getItemCount()",String.valueOf(billRecords.size()));
        return billRecords.size();
    }

    // 内部类：用于描述RecyclerView子项的布局
    public class MyViewHolder extends RecyclerView.ViewHolder {
        View tabView;   //标签布局
        TextView tableName;
        LinearLayout linearLayout;
        TextView billinfo_value;
        TextView billinfo_class;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tabView = itemView;
            tableName = (TextView) itemView.findViewById(R.id.txv_billsDate);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_billinfo);
            billinfo_class = (TextView) itemView.findViewById(R.id.txv_billinfo_class);
            billinfo_value = (TextView )itemView.findViewById(R.id.txv_billinfo_value);
        }
        public View getTabView(){
            return tabView;
        }
    }

    //启动新活动 显示详细信息
    private void showNewActivity(Context context, BillRecord billRecord) {
        Intent intent = new Intent(context, BillInfoActivity.class);
        intent.putExtra(INTENT_PARAM_BILLRECORD,billRecord);
        context.startActivity(intent);
    }

}
