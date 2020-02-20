package com.example.novatrip.SCHEDULE.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.novatrip.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

public class ScheduleNavigationPlanActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerview_planList;
    TextView tv_addPlan;
    public String TAG = "ScheduleNavigationPlanActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_navigation_plan);

        recyclerview_planList = findViewById(R.id.recyclerview_planList);
        //TODO: 계획 리스트 리사이클러뷰.

        tv_addPlan = findViewById(R.id.tv_addPlan);
        tv_addPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //여행계획 추가하기
            case R.id.tv_addPlan:
                Log.d(TAG, "onClick: 여행계획 추가하기");
                Intent intent = new Intent(ScheduleNavigationPlanActivity.this, ScheduleCalender.class);
                startActivity(intent);
                break;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        initBottonNavigationView(this,navView,1);
    }



    public static void initBottonNavigationView(final Context activity , BottomNavigationView bottomNavigationView, final int number) {
        Log.d("ScheduleNavigation", "initBottonNavigationView: 하단버튼 클릭~! ");
//        ScheduleBottomNavigationViewHelper.disableShiftMode(bottomNavigationView ) ;

        removeNavigationShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(number);
        menuItem.setCheckable(true);
        menuItem.setChecked(true);

//  app:labelVisibilityMode="unlabeled"
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //여행지 클릭시
                    case R.id.navigation__pin:
                        if(number != 0){
                            Log.d("ScheduleNavigation", "1번 클릭");
                            Intent intent = new Intent(activity.getApplicationContext(), ScheduleNavigationLocationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                            ((Activity)(activity)).overridePendingTransition(0, 0);

                        }
                        break;
                    //일정 클릭시
                    case R.id.navigation_plan:
                        if(number != 1){
                            Log.d("ScheduleNavigation", "2번 클릭");
                            Intent intent = new Intent(activity.getApplicationContext(), ScheduleNavigationPlanActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                            ((Activity)(activity)).overridePendingTransition(0, 0);
                        }
                        break;
                    //채팅 클릭시
                    case R.id.navigation_chat:
                        if(number != 2){
                            Log.d("ScheduleNavigation", "3번 클릭");
                            Intent intent = new Intent(activity.getApplicationContext(), ScheduleNavigationChatActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                            ((Activity)(activity)).overridePendingTransition(0, 0);
                        }
                        break;
                    //번역 클릭시
                    case R.id.navigation_tanstor:
                        if(number != 3){
                            Log.d("ScheduleNavigation", "4번 클릭");
                            Intent intent = new Intent(activity.getApplicationContext(), ScheduleNavigationTransforActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                            ((Activity)(activity)).overridePendingTransition(0, 0);
                        }
                        break;
                    //내정보 클릭시
                    case R.id.navigation_mypage:
                        if(number != 4){
                            Log.d("ScheduleNavigation", "5번 클릭");
                            Intent intent = new Intent(activity.getApplicationContext(), ScheduleNavigationMypageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                            ((Activity)(activity)).overridePendingTransition(0, 0);
                        }
                        break;
                }
                return false;
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public static void removeNavigationShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        menuView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_AUTO);
        menuView.buildMenuView();
    }


}
