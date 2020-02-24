package com.example.novatrip.SCHEDULE.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterFragmentPlace;
import com.example.novatrip.SCHEDULE.Unit.ItemTravelDetail;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class SchedulePlaceDetailActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager ;
//    AdapterFragmentMypage pageAdapter;
    AdapterFragmentPlace adapterFragmentPlace;

    TabItem tab_info;
    TabItem tab_famousRestaurant;
    TabItem tab_recommendation;
    TabItem tab_touristAttraction;
    String TAG  =  "SchedulePlaceDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_place_detail);

        tabLayout = findViewById(R.id.tablayout);

        tab_info = findViewById(R.id.tab_info); // 장소 소개
        tab_famousRestaurant = findViewById(R.id.tab_famousRestaurant);// 맛집
        tab_recommendation = findViewById(R.id.tab_recommendation);// 관광소
        tab_touristAttraction = findViewById(R.id.tab_touristAttraction);// 추천

        viewPager = findViewById(R.id.viewPager);


        Intent intent = getIntent();
        ItemTravelDetail itemTravelDetail = intent.getParcelableExtra("itemTravelDetail");
        Log.d(TAG, "onCreate: 정보 받음 getUnixtime_travel_plan_detail "+ itemTravelDetail.getUnixtime_travel_plan_detail());



        adapterFragmentPlace = new AdapterFragmentPlace(getSupportFragmentManager() , itemTravelDetail );
        viewPager.setAdapter(adapterFragmentPlace);
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
                             R.color.WHITE));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                                 R.color.WHITE));
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