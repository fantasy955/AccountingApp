package com.experiment.lenovo.accountingsoftware.accuntingpage;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.experiment.lenovo.accountingsoftware.R;

import java.util.List;

public class IncomeFragmentRecycleViewAdapter  extends RecyclerView.Adapter<IncomeFragmentRecycleViewAdapter.MyViewHolder>   {

//    Context context;
    int imageWidth;
    int imageHeight;
    TableLayout tableLayout;
    String billClass;
    private List<IncomeFragmentRecycleViewAdapterItem> tabList;
    public IncomeFragmentRecycleViewAdapter(List<IncomeFragmentRecycleViewAdapterItem> incomeFragmentRecycleViewAdapterItems,TableLayout tableLayout) {
        tabList = incomeFragmentRecycleViewAdapterItems;
        this.tableLayout = tableLayout;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DisplayMetrics displayMetrics =  viewGroup.getContext().getResources().getDisplayMetrics();
        imageWidth = displayMetrics.widthPixels/4;
        imageHeight = imageWidth * 292 / 270;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.billclass_image_item, viewGroup, false);
        final  MyViewHolder myViewHolder = new MyViewHolder(view);

        //为标签布局添加点击事件
        myViewHolder.tabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();
                IncomeFragmentRecycleViewAdapterItem tab = tabList.get(position);
//                Toast.makeText(v.getContext(), "点击了：" + tab.getBillClassName(), Toast.LENGTH_LONG).show();
                AccountingPageTabFragment.setBillType("1");
                tableLayout.setVisibility(View.VISIBLE);
                billClass = tabList.get(position).getBillClassName();
                Toast.makeText(v.getContext(),"您选择了"+billClass,Toast.LENGTH_SHORT).show();
                AccountingPageTabFragment.setBillClass(billClass);
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // 通过参数 position 获得 RecyclerView 当前子项的实例
        IncomeFragmentRecycleViewAdapterItem tab = tabList.get(i);
        myViewHolder.billClassImageView.setImageResource(tab.getBillClassImage());
    }

    @Override
    public int getItemCount() {
        return tabList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View tabView;   //标签布局
        ImageView billClassImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tabView = itemView;
            billClassImageView = (ImageView) itemView.findViewById(R.id.accountingpageitem_imageView_billclass);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) billClassImageView.getLayoutParams();
            params.width = imageWidth;
            params.height = imageHeight;
            billClassImageView.setLayoutParams(params);
        }
        public View getTabView(){
            return tabView;
        }
    }
}
