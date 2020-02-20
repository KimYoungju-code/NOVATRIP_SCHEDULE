package com.example.novatrip.SCHEDULE.UI;

import android.os.Bundle;

import com.example.novatrip.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static com.example.novatrip.SCHEDULE.UI.ScheduleNavigationPlanActivity.initBottonNavigationView;

public class ScheduleNavigationChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_navigation_chat);



    }

    @Override
    protected void onResume() {
        super.onResume();
        //하단 탭버튼
        BottomNavigationView navView = findViewById(R.id.nav_view);
        initBottonNavigationView(this,navView,2);
    }
}
