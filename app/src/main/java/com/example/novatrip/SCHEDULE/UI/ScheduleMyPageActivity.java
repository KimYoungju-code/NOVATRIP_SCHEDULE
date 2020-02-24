package com.example.novatrip.SCHEDULE.UI;

import android.os.Build;
import android.os.Bundle;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterFragmentMypage;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class ScheduleMyPageActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager ;
    AdapterFragmentMypage pageAdapter;
    TabItem tabChats;
    TabItem tabStatus;
    TabItem tabCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_my_page);

        tabLayout = findViewById(R.id.tablayout);
        tabChats = findViewById(R.id.tabChats);
        tabStatus = findViewById(R.id.tabStatus);

        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new AdapterFragmentMypage(getSupportFragmentManager() );
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
//                            R.color.colorAccent));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.WHITE));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                                R.color.WHITE));
                    }
                } else if (tab.getPosition() == 2) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
//                            android.R.color.darker_gray));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                            android.R.color.darker_gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                                android.R.color.darker_gray));
                    }
                } else {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
//                            R.color.colorPrimary));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.WHITE));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                                R.color.WHITE));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}