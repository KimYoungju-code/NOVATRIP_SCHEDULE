package com.example.novatrip.SCHEDULE.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulAddLocal;
import com.example.novatrip.SCHEDULE.Adapter.AdapterSchedulChoiceLocal;
import com.example.novatrip.SCHEDULE.ClickLisener.ClickLisenerSchedulLocalItem;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Retrofit.retrofit;
import com.example.novatrip.SCHEDULE.Unit.Local;
import com.example.novatrip.SCHEDULE.Unit.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 여행지 선택
 * **/
public class ScheduleAddLocal extends AppCompatActivity {

public  String TAG = "ScheduleAddLocal";
    RecyclerView recyclerview_choiceLocal ,recyclerview_localList;
    AdapterSchedulAddLocal adapterSchedulAddLocal;
    AdapterSchedulChoiceLocal adapterSchedulChoiceLocal;
    Button btn_add_travelPriod;
    RetroBaseApi retroBaseApi;
    TextView input_keyword ;

    ArrayList<Local> choiceLocalList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add_local);

        //초기화
        recyclerview_choiceLocal = findViewById(R.id.recyclerview_choiceLocal);
        recyclerview_localList = findViewById(R.id.recyclerview_localList);
        btn_add_travelPriod = findViewById(R.id.btn_add_travelPriod);
        input_keyword = findViewById(R.id.input_keyword);
        choiceLocalList = new ArrayList<>();
        //지역을 보여주는 세로 리사이클러뷰 init
        initRecyclerviewLocalList ();
        //선택한 지역을 보여주는 가로 리사이클러뷰
        initRecyclerviewChoiceLocalList ();

        //input_keyword 키업
        input_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = input_keyword.getText().toString();
                Log.d(TAG, "onTextChanged: 검색한 키워드 " + keyword);
                searchKeyword(keyword);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //지역 리사이클러뷰 아이템 클릭시.
        adapterSchedulAddLocal.ClickListener_clickLisenerSchedulLocalItem(new ClickLisenerSchedulLocalItem() {
            @Override
            public void OnItemClick(Local local, int postision) {

                //TODO: 예외처리 같은 지역 중복 등록 못하게 해야함.
                //클릭한 아이템의 idx와 지역 이름을 받음.
                Log.d(TAG, "OnItemClick: idx "+local.getIdx_local());
                Log.d(TAG, "OnItemClick: name "+local.getName());

                //선택한 아이템을 다시 선택한 지역 리사이클러뷰에 추가.
                choiceLocalList.add(local);
                adapterSchedulChoiceLocal.setLocalList(choiceLocalList);
                adapterSchedulChoiceLocal.notifyItemChanged(choiceLocalList.size());
            }
        });

        btn_add_travelPriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: 기간 등록 ");
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("choiceLocalList",   choiceLocalList);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }

    //사용자가 입력한 키워드와 일치하는 값이 서버에 있는지 확인.
    public void searchKeyword (String keyword ){
        retroBaseApi = retrofit.getRetrofit().create(RetroBaseApi.class);
        retroBaseApi.postSearchLoaclKeyWord(keyword ).enqueue(new Callback<List<Local>>() {

            @Override
            public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {

                ArrayList<Local> localArrayList = new ArrayList<>();

                SharedPreferences sharedPreferences =  getSharedPreferences("LOCAL", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                for(int i = 0; i < response.body().size() ; i++){
                    localArrayList.add(i,response.body().get(i));
                    //쉐어드에  key:인덱스  value :로컬이름을 저장한다.
                    editor.putString(String.valueOf(response.body().get(i).getIdx_local()) , response.body().get(i).getName());
                    Log.d(TAG, "onResponse: localList.get(i).getName() "+response.body().get(i).getName());
                }

                editor.apply();
                adapterSchedulAddLocal.setLocalList(localArrayList);
                adapterSchedulAddLocal.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Local>> call, Throwable t) {

            }
        });
    }


    //지역을 보여주는 세로 리사이클러뷰
    public void initRecyclerviewLocalList (){
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerview_localList.setLayoutManager(LayoutManager);
        adapterSchedulAddLocal = new AdapterSchedulAddLocal(getApplicationContext() );
        recyclerview_localList.setAdapter(adapterSchedulAddLocal);
        LayoutManager.setReverseLayout(true);
        LayoutManager.setStackFromEnd(true);
    }

    //선택한 지역 보여주는 가로 리사이클러뷰
    public void initRecyclerviewChoiceLocalList (){

        @SuppressLint("WrongConstant")
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        recyclerview_choiceLocal.setLayoutManager(LayoutManager);
        adapterSchedulChoiceLocal = new AdapterSchedulChoiceLocal(getApplicationContext());
        recyclerview_choiceLocal.setAdapter(adapterSchedulChoiceLocal);
        LayoutManager.setReverseLayout(true);
        LayoutManager.setStackFromEnd(true);

    }



}
