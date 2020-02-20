package com.example.novatrip.SCHEDULE.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulAddLocal;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulResultLocal;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Retrofit.retrofit;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.Travel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleAddTravel extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText input_enterTravelName ;
    TextInputLayout input_choiceTravelLocal;
    TextView tv_travelPriod;
    ArrayList<String> arraylist_travelPriodList;
    ArrayList<Local> localArrayList;
    RecyclerView recyclerview_tavelLocalList;
    AdapterSchedulResultLocal adapterSchedulResultLocal;
    Button btn_add_travelPriod;
    RetroBaseApi retroBaseApi;
    final static  int choiceTravelLocal =  1;
     public String TAG = "ScheduleAddTravel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add_travel);

        //초기화
        tv_travelPriod = findViewById(R.id.tv_travelPriod);
        input_choiceTravelLocal = findViewById(R.id.input_choiceTravelLocal);
        input_enterTravelName = findViewById(R.id.input_enterTravelName);
        recyclerview_tavelLocalList = findViewById(R.id.recyclerview_tavelLocalList);
        btn_add_travelPriod= findViewById(R.id.btn_add_travelPriod);
        localArrayList=new ArrayList<>();
        initRecyclerviewLocalList ();

        //클릭 리스너
        input_choiceTravelLocal.setOnClickListener(this);
        btn_add_travelPriod.setOnClickListener(this);

        //사용자가 선택한 기간의 unixtime list
        arraylist_travelPriodList =  getIntent().getStringArrayListExtra("itemTravelPlanArrayList");

        //여행 시작일 종료일 set
        String startDay = getIntent().getStringExtra("startDay");
        String endDay = getIntent().getStringExtra("endDay");
        String priod = startDay+" ~ "+endDay;
        tv_travelPriod.setText(priod);
        Log.d(TAG, "onCreate:arraylist_travelPriodList " +arraylist_travelPriodList.size());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //여행등록.
            case R.id.input_choiceTravelLocal:
                Log.d(TAG, "onClick: 로컬 선택");
                Intent intent = new Intent(getApplicationContext(), ScheduleAddLocal.class);
                startActivityForResult(intent , choiceTravelLocal );
                break;


            case R.id.btn_add_travelPriod:
                //서버에 입력한 내용을 보내서 db에 저장.
                saveTravel();
                break;

        }
    }
public void saveTravel(){
        //여행이름
        String name_travel = input_enterTravelName.getText().toString();

        //사용자 idx
        SharedPreferences sharedPreferences = getSharedPreferences("Login",MODE_PRIVATE);
        int idx_user = sharedPreferences.getInt("idx",0);

        //여행 지역 idx list
        String travel_local_list = "";
           for (int i=0 ; i < localArrayList.size(); i++){
               if(i == 0){
                   travel_local_list =  String.valueOf(localArrayList.get(i).getIdx_local());
               }else{
                   travel_local_list += ","+ String.valueOf(localArrayList.get(i).getIdx_local());
               }
           }

       //여행 기간 unixtiem list
    String  travel_priod_list = "";
    String time_travel_start = "";
    String time_travel_end = "";

    for (int i=0 ; i < arraylist_travelPriodList.size(); i++){
        if(i == 0){
            travel_priod_list =  String.valueOf(arraylist_travelPriodList.get(i));
            time_travel_start =   String.valueOf(arraylist_travelPriodList.get(i));
        }else{
            travel_priod_list += ","+String.valueOf(arraylist_travelPriodList.get(i));
            if(i == arraylist_travelPriodList.size()-1 ){
                time_travel_end =   String.valueOf(arraylist_travelPriodList.get(i));
            }
        }
    }
    Log.d(TAG, "saveTravel: 여행 이름 "+ name_travel);
    Log.d(TAG, "saveTravel:   idx "+ idx_user);
    Log.d(TAG, "saveTravel: 여행 result "+ travel_local_list);
    Log.d(TAG, "saveTravel: 여행 itemTravelPlanArrayList "+ travel_priod_list);
    Log.d(TAG, "saveTravel: 여행 travel_end "+ time_travel_end);
    Log.d(TAG, "saveTravel: 여행 travel_start "+ time_travel_start);


    //서버에 저장.

    /** Call<List<Travel>> postSaveTravelPlan (@Field("idx_user") int idx_user,
     @Field("idx_user") String travel_name, @Field("travel_local") String travel_local
     ,@Field("idx_user") String travel_start  ,@Field("idx_user") String travel_end
     ,@Field("idx_user") String travel_priod_list);
     * */

    retroBaseApi = retrofit.getRetrofit().create(RetroBaseApi.class);
    retroBaseApi.postSaveTravelPlan(idx_user , name_travel , time_travel_start ,time_travel_end  , travel_local_list ,travel_priod_list  )
            .enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {

                    Log.d(TAG, "onResponse: 서버에 travel 정보 저장");
                    Log.d(TAG, "onResponse:  "+response.body());

                    //서버에 Travel Plan 저장 후 idx만 다음 페이지로 넘김.
                    Intent intent  = new Intent(getApplicationContext(), ScheduleTravelDetailActivity.class);
                    intent.putExtra("idx_travel_plan",response.body());
                    startActivity(intent);

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d(TAG, "onFailure: " +t.getMessage());

                }
            });
    
    
    
}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == choiceTravelLocal && resultCode == RESULT_OK && null != data) {

            Log.d(TAG, "onActivityResult: 지역 선택. ");
             localArrayList   =  data.getParcelableArrayListExtra("choiceLocalList");
            Log.d(TAG, "onActivityResult:  localArrayList.toString() "+localArrayList.toString());

            for(int i = 0 ; i <localArrayList.size()  ; i++){
                Log.d(TAG, "onActivityResult: localArrayList.get(i).getName() "+localArrayList.get(i).getName());
            }

            adapterSchedulResultLocal.setLocalList(localArrayList);
            adapterSchedulResultLocal.notifyDataSetChanged();

        }
    }

    //지역을 보여주는 세로 리사이클러뷰
    public void initRecyclerviewLocalList (){

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerview_tavelLocalList.setLayoutManager(LayoutManager);
        adapterSchedulResultLocal = new AdapterSchedulResultLocal(getApplicationContext() );
        recyclerview_tavelLocalList.setAdapter(adapterSchedulResultLocal);
        LayoutManager.setReverseLayout(true);
        LayoutManager.setStackFromEnd(true);

    }

}
