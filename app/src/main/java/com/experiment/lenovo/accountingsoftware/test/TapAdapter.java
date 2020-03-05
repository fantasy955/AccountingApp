package com.experiment.lenovo.accountingsoftware.test;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TapAdapter extends FragmentPagerAdapter {
    public static final  String[] TITLES = new String[] {"明细","记账","我的"};
    private List<Fragment> fragmentList;
    public TapAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList){
        super((fragmentManager));
        this.fragmentList = fragmentList;
    }
    @Override
    public  int getCount(){
        return TITLES.length;
    }
    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }


}
