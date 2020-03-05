package com.experiment.lenovo.accountingsoftware.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.experiment.lenovo.accountingsoftware.R;


@SuppressLint("ValidFragment")
public class TapFragment extends Fragment {
    private int selection = 0;
    public TapFragment(){

    }
    public TapFragment(int selection){
        this.selection = selection;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_item_detail, null);
        switch (selection){
            case 0:
                view = inflater.inflate(R.layout.tab_item_detail, null);
                return view;
            case 1:
                view = inflater.inflate(R.layout.tab_item_accounting, null);
                return view;
            case 2:
                view = inflater.inflate(R.layout.tab_item_myinfo, null);
                return view;
        }
        return view;
    }

}
