package com.example.novatrip.SCHEDULE.UI;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulOlympicDailyPlan;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulResultLocal;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Retrofit.retrofit;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.OlympicGame;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 일자별 올림픽 스케쥴을 보여주고 사용자가 추가할 경기 일정을 선택하는 엑티비티
 * */


public class ScheduleOlympicDailyScedule extends FragmentActivity implements OnMapReadyCallback , View.OnClickListener {


    String TAG = "ScheduleOlympicDailyScedule";
    RecyclerView recyclerview_olympicDailySchedule;
    private GoogleMap mMap;
    TextView tv_olympic_day ,tv_schedule_registration;
    AdapterSchedulOlympicDailyPlan adapterSchedulOlympicDailyPlan;
    RetroBaseApi retroBaseApi;
    //올림픽 경기 일정에 대한 정보를 담고있는 리사이클러뷰
    ArrayList<OlympicGame> localArrayList;

    Intent getIntent;// ScheduleOlympicDailyScedule에서 받은 인텐트
    //상세일정을 저장할 때 필요한 정보.
    int idx_travel_plan;
    int position;
    String unixtime_travel_plan_detail;
    ArrayList<OlympicGame> olympicGameArrayList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_olympic_daily_scedule);

        //초기화
        localArrayList = new ArrayList<>();
        olympicGameArrayList = new ArrayList< >();
        tv_schedule_registration = findViewById(R.id.tv_schedule_registration);
        tv_schedule_registration.setOnClickListener(this);
        //TODO:올림픽 경기장 위치 지도 표시
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        //초기화
        tv_olympic_day = findViewById(R.id.tv_olympic_day);
        initRecyclerviewLocalList ();

        //일정을 추가하기로 한 날짜의 unixtime
          getIntent = getIntent();
          position = getIntent.getIntExtra("position",10000);
          unixtime_travel_plan_detail =getIntent.getStringExtra("unixTime");
          idx_travel_plan = getIntent.getIntExtra("idx_travel_plan",10000);
        Log.d(TAG, "onCreate: unixTime "+unixtime_travel_plan_detail);
        Log.d(TAG, "onCreate: position "+position);

        //YYYY-MM-DD 형태로 바꿈.
        String date =  UnixTimeToDay(unixtime_travel_plan_detail);
        Log.d(TAG, "onCreate: date"+date);
        tv_olympic_day.setText(date);
        locadOlympicSchedule(date);
        //해당 일자에 열리는 경기 일정을 서버에 요청


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_schedule_registration:
                setResult(Activity.RESULT_OK);
                //일정 등록하기 클릭하면 사용자가 일정에 추가하려고 선택한 경기 리스트를 adapter에서 받아온다.
                Log.d(TAG, "onClick: 클릭");
                olympicGameArrayList =  adapterSchedulOlympicDailyPlan.getcheckOlympicGameArrayList();
                //메인 엑티비티로 전달한다.
                Log.d(TAG, "onClick: "+olympicGameArrayList.size());

                Intent intent_result = new Intent();
                intent_result.putParcelableArrayListExtra("olympicGameArrayList",   olympicGameArrayList);
                intent_result.putExtra("unixTime", unixtime_travel_plan_detail);
                intent_result.putExtra("position",position);

                Log.d(TAG, "onClick: ScheduleTravelDetailActivity에 전달하는 정보");
                Log.d(TAG, "onClick: position "+ position);
                Log.d(TAG, "onClick: unixTime "+ unixtime_travel_plan_detail);
                Log.d(TAG, "onClick: olympicGameArrayList "+ olympicGameArrayList.toString());
                setResult(RESULT_OK, intent_result);
                finish();
                Log.d(TAG, "onClick: 인텐트 전달!");
                break;
        }
    }


    //일자별 올림픽 경기 시간표 정보 + 경기정보 가져오기.
    public void locadOlympicSchedule(String date){
        retroBaseApi = retrofit.getRetrofit().create(RetroBaseApi.class);
        retroBaseApi.postLoadOlympicDailyPlan(date)
                . enqueue(new Callback<List<OlympicGame>>() {
                    @Override
                    public void onResponse(Call<List<OlympicGame>> call, Response<List<OlympicGame>> response) {

                        Log.d(TAG, "onResponse " +response.body().size());

                        for(int i = 0; i < response.body().size() ; i++){
                            localArrayList.add(i,response.body().get(i));
                            Log.d(TAG, "onResponse: localArrayList.get(i).getName_olympic_game() "+localArrayList.get(i).getName_olympic_game());
                        }

                        //올림픽 경기 시간표를 보여줄 리사이클러뷰에 경기 일정 정보가 담긴 arraylist를 set한다.
                        adapterSchedulOlympicDailyPlan.setLocalList(localArrayList);
                        adapterSchedulOlympicDailyPlan.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<OlympicGame>> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }
    //올림픽 경기 일정을 보여주는 리사이클러뷰
    public void initRecyclerviewLocalList (){
        recyclerview_olympicDailySchedule=findViewById(R.id.recyclerview_olympicDailySchedule);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerview_olympicDailySchedule.setLayoutManager(LayoutManager);
        adapterSchedulOlympicDailyPlan = new AdapterSchedulOlympicDailyPlan(getApplicationContext() );
        recyclerview_olympicDailySchedule.setAdapter(adapterSchedulOlympicDailyPlan);
        LayoutManager.setReverseLayout(false);
        LayoutManager.setStackFromEnd(false);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



    public static String UnixTimeToDay(String unixTime) {
        Date date = new Date(Long.valueOf(unixTime));
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        return time.format(date);
    }
}
