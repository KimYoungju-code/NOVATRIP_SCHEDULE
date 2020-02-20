package com.example.novatrip.SCHEDULE.UI;

import android.os.Bundle;

import com.example.novatrip.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.novatrip.SCHEDULE.UI.ScheduleNavigationPlanActivity.initBottonNavigationView;

public class ScheduleNavigationTransforActivity extends AppCompatActivity {
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_navigation_transfor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //하단 탭버튼
        BottomNavigationView navView = findViewById(R.id.nav_view);
        initBottonNavigationView(this,navView,3);
    }


}
