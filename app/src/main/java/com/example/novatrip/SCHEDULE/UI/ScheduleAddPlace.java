package com.example.novatrip.SCHEDULE.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterFragmentAddPlace;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulTravelPlanAndDetail;
import com.example.novatrip.SCHEDULE.Adapter.AdapterScheduleChoicePlace;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ScheduleAddPlace  extends AppCompatActivity {

    String TAG = "ScheduleAddPlace";

    TabLayout tabLayout;
    ViewPager viewPager ;
    //    AdapterFragmentMypage pageAdapter;
    AdapterFragmentAddPlace adapterFragmentAddPlace;

    TabItem tab_all;
    TabItem tab_sights;
    TabItem tab_restaurant;
    TabItem tap_shopping;

    TextView tv_save_choicePlace;

    //카테고리.
    public static int category_place_all = 1000;
    public static int category_place_restaurant = 1;
    public static int category_place_shopping = 2;
    public static int category_place_sights = 3;


    //사용자가 선택한 place 정보를 보여줄 가로 리사이클러뷰.
    RecyclerView recycler_view_choice_place;
     AdapterScheduleChoicePlace adapterScheduleChoicePlace;

    String unixTime;
    int position_travel_plan, idx_travel_plan, idx_local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add_place);

        tabLayout = findViewById(R.id.tablayout);
        tab_all = findViewById(R.id.tab_all); // 모든 장소
        tab_sights = findViewById(R.id.tab_sights);// 관광지
        tab_restaurant = findViewById(R.id.tab_restaurant);// 식당
        tap_shopping = findViewById(R.id.tap_shopping);// 쇼핑
        viewPager = findViewById(R.id.viewPager);
        tv_save_choicePlace = findViewById(R.id.tv_save_choicePlace);

        //사용자가 선택한 장소를 보여줄 가로 리사이클러뷰 init
        Log.d(TAG, "onCreate: 사용자가 선택한 장소를 보여줄 가로 리사이클러뷰 init ");
        recycler_view_choice_place  = findViewById(R.id.recycler_view_choice_place);
        initRecyclerviewChoicePlace();


        tv_save_choicePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("choicePlaceList",   adapterScheduleChoicePlace.getPlaceArrayList());
                intent.putExtra("unixTime", getIntent().getStringExtra("unixTime"));
                intent.putExtra("position",position_travel_plan);

                setResult(RESULT_OK, intent);
                finish();
                Log.d(TAG, "onClick: 인텐트 전달!");
            }
        });

        //상세 일정페이지에서 받은 정보 .
        String unixTime = getIntent().getStringExtra("unixTime");
        position_travel_plan = getIntent().getIntExtra("position_travel_plan" , 1000);
        idx_travel_plan = getIntent().getIntExtra("idx_travel_plan", 1000);
        idx_local = getIntent().getIntExtra("idx_local", 1000);

        Log.d(TAG, "onCreate: 메인에서 받은 정보 unixTime " +unixTime);
        Log.d(TAG, "onCreate: 메인에서 받은 정보 position " +position_travel_plan);
        Log.d(TAG, "onCreate: 메인에서 받은 정보 idx_travel_plan " +idx_travel_plan);
        Log.d(TAG, "onCreate: 메인에서 받은 정보 idx_local " +idx_local);



        adapterFragmentAddPlace = new AdapterFragmentAddPlace(getSupportFragmentManager() , unixTime ,position_travel_plan,idx_travel_plan,idx_local  ,adapterScheduleChoicePlace);
        viewPager.setAdapter(adapterFragmentAddPlace);
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

//사용자가 선택한 지역 정보를 보여줄 가로 리사이클러뷰
    public void initRecyclerviewChoicePlace() {
        @SuppressLint("WrongConstant") LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        recycler_view_choice_place.setLayoutManager(LayoutManager);
        adapterScheduleChoicePlace = new AdapterScheduleChoicePlace(getApplicationContext() );
        recycler_view_choice_place.setAdapter(adapterScheduleChoicePlace);
        LayoutManager.setReverseLayout(false);
        LayoutManager.setStackFromEnd(false);
    }

}