package com.experiment.lenovo.accountingsoftware.accuntingpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.experiment.lenovo.accountingsoftware.R;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class AccountingActivity extends FragmentActivity {
    private Button btnSubmit;
    private EditText etComment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting);
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");


        Fragment fragment0 = new AccountingPageTabFragment(0,user);
        Fragment fragment1 = new AccountingPageTabFragment(1,user);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragment0);
        fragmentList.add(fragment1);

        ViewPager viewPager = (ViewPager) findViewById(R.id.accoutingpage_viewpager);
        AccountingPageAdapter tabAdapter = new AccountingPageAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(tabAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.accoutingpage_tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }
}
