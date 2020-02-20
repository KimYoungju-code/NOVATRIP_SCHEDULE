package com.example.novatrip.SCHEDULE.UI;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.example.novatrip.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 달력에서 여행기간 선택
 * **/

public class ScheduleCalender extends AppCompatActivity implements  View.OnClickListener {

    private CalendarView calendarView;
    Button btn_add_travelPriod;

    String TAG = " ScheduleCalender ";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_calender);



        //등록
        btn_add_travelPriod =findViewById(R.id.btn_add_travelPriod);
        btn_add_travelPriod.setOnClickListener(this);
        initViews();

    }


    private void initViews() {
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setSelectionType(SelectionType.RANGE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calender, menu);
        return true;
    }






    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.clear_selections:
//                clearSelectionsMenuClick();
//                break;

            case R.id.btn_add_travelPriod:

                Log.d(TAG, "onClick:캘린더 선택 ");

                List<Calendar> days = calendarView.getSelectedDates();

                //다음 엑티비티로 넘길 리스트.
                ArrayList<String> travelPriodList = new ArrayList<>();

                String result="";
                String startDay = "";
                String endDay = "";

                for( int i=0; i<days.size(); i++)
                {
                    Calendar calendar = days.get(i);
                    Log.d(TAG, "onClick: "+calendar.getTimeInMillis());
                    travelPriodList.add(String.valueOf(calendar.getTimeInMillis()));
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    final int month = calendar.get(Calendar.MONTH);
                    final int year = calendar.get(Calendar.YEAR);
                    String week = new SimpleDateFormat("EE").format(calendar.getTime());
                    String day_full = year + "년 "+ (month+1)  + "월 " + day + "일 " + week + "요일";

                    if(i == 0){
                        startDay = year + "년 "+ (month+1)  + "월 " + day + "일 " + week + "요일";
                    }else if(i == days.size()-1){
                        endDay  = year + "년 "+ (month+1)  + "월 " + day + "일 " + week + "요일";
                    }
                    result += (day_full + "\n");

                }

                Intent intent = new Intent(getApplicationContext(),ScheduleAddTravel.class);
                intent.putStringArrayListExtra("itemTravelPlanArrayList",travelPriodList);
                intent.putExtra("startDay",startDay);
                intent.putExtra("endDay",endDay);
                startActivity(intent);

                Toast.makeText(ScheduleCalender.this, result, Toast.LENGTH_LONG).show();


            default:

        }
    }
}