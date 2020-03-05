package com.experiment.lenovo.accountingsoftware.accuntingpage;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class AccountingPageAdapter extends FragmentPagerAdapter {
    public static final  String[] TITLES = new String[] {"支出","收入",};
    private List<Fragment> fragmentList;
    public AccountingPageAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList){
        super((fragmentManager));
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

}
