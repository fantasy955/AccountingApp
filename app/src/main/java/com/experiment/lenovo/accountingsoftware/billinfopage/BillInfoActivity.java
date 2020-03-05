package com.experiment.lenovo.accountingsoftware.billinfopage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.experiment.lenovo.accountingsoftware.R;
import com.experiment.lenovo.accountingsoftware.common.BillRecord;
import com.experiment.lenovo.accountingsoftware.common.Constants;

import static com.experiment.lenovo.accountingsoftware.common.Constants.EXPEND;
import static com.experiment.lenovo.accountingsoftware.common.Constants.INTENT_PARAM_BILLRECORD;
import static com.experiment.lenovo.accountingsoftware.common.Constants.STRING_EXPEND;
import static com.experiment.lenovo.accountingsoftware.common.Constants.STRING_INCOME;

public class BillInfoActivity extends Activity { //显示账单记录详细信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billinfo);
        BillRecord billRecord = getIntent().getParcelableExtra(INTENT_PARAM_BILLRECORD);

        ImageView imageView_billClass = findViewById(R.id.imageView_billclass);
        TextView textView_billtype = findViewById(R.id.TextView_billtype);
        TextView textView_billvalue = findViewById(R.id.TextView_billvalue);
        TextView textView_billdate = findViewById(R.id.TextView_billdate);
        TextView textView_billnote = findViewById(R.id.TextView_billnote);
//        TextView textView_billclass = findViewById(R.id.textView_billclass);
        if(billRecord.getBillType().equals(EXPEND)){
            textView_billtype.setText(STRING_EXPEND);
            int i = 0;
            for( ; i< Constants.billClassNames.length ;i++){
                if(Constants.billClassNames[i].equals(billRecord.getBillClass())){
                    break;
                }
            }
            imageView_billClass.setImageResource(Constants.billClassImages[i]);
        }else{
            textView_billtype.setText(STRING_INCOME);
            int i = 0;
            for( ; i< Constants.incomeClassNames.length ;i++){
                if(Constants.incomeClassNames[i].equals(billRecord.getBillClass())){
                    break;
                }
            }
            imageView_billClass.setImageResource(Constants.incomeClassImages[i]);
        }

        textView_billdate.setText(billRecord.getDateOfBill().toStringChinese());

        textView_billvalue.setText(billRecord.getValues());

        textView_billnote.setText(billRecord.getNote());

        //        textView_billclass.setText(billRecord.getBillClass());

    }
}
