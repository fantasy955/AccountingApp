package com.experiment.lenovo.accountingsoftware.accuntingpage;

import android.util.DisplayMetrics;
import android.util.Log;
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
import com.experiment.lenovo.accountingsoftware.common.Constants;

import java.util.List;

public class ExpendFragmentRecycleViewAdapter extends  RecyclerView.Adapter<ExpendFragmentRecycleViewAdapter.MyViewHolder>  {
//    Context context;
    int imageWidth;
    int imageHeight;
    TableLayout tableLayout;
    String billClass;

    private View.OnClickListener expendFragmentRecycleViewAdapterOnClickListener;
    private List<ExpendFragmentRecycleViewAdapterItem> tabList;
    public ExpendFragmentRecycleViewAdapter(List<ExpendFragmentRecycleViewAdapterItem> expendFragmentRecycleViewAdapterItems, TableLayout tableLayout) {
        tabList = expendFragmentRecycleViewAdapterItems;
        this.tableLayout = tableLayout;
//        expendFragmentRecycleViewAdapterOnClickListener = (View.OnClickListener) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        context = viewGroup.getContext();
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
                ExpendFragmentRecycleViewAdapterItem tab = tabList.get(position);
                tableLayout.setVisibility(View.VISIBLE);
                billClass = tabList.get(position).getBillClassName();
                Toast.makeText(v.getContext(),"您选择了"+billClass,Toast.LENGTH_SHORT).show();
                AccountingPageTabFragment.setBillClass(billClass);

                //使用显式Intent启动活动
            }
        });

        return myViewHolder;
    }

    public String getBillClass(){
        return billClass;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // 通过参数 position 获得 RecyclerView 当前子项的实例
        ExpendFragmentRecycleViewAdapterItem tab = tabList.get(i);
        myViewHolder.billClassImageView.setImageResource(tab.getBillClassImage());
    }

    @Override
    public int getItemCount() {
        return tabList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // 内部类：用于描述RecyclerView子项的布局
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
