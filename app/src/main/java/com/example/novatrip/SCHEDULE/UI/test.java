package com.example.novatrip.SCHEDULE.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.novatrip.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
        initBottonNavigationView(0);

    }


    public void initBottonNavigationView(int number){

        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavigationView);
        ScheduleBottomNavigationViewHelper.disableShiftMode(bottomNavigationView );
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(number);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //여행지 클릭시
                    case R.id.navigation__pin :

                        break;
                    //일정 클릭시
                    case R.id.navigation_plan :
                        break;
                    //채팅 클릭시
                    case R.id.navigation_chat :
                        break;
                    //번역 클릭시
                    case R.id.navigation_tanstor :
                        break;
                    //내정보 클릭시
                    case R.id.navigation_mypage :
                        break;

                }
                return false;
            }
        });
    }

}
