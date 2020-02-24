package com.example.novatrip.SCHEDULE.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.example.novatrip.SCHEDULE.Retrofit.retrofit;
import com.example.novatrip.SCHEDULE.Unit.User;
import com.facebook.stetho.Stetho;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.novatrip.SCHEDULE.Unit.Unit.myUnit;


/**
 * 로그인 페이지
 * **/

public class ScheduleLoginActivity extends AppCompatActivity  implements View.OnClickListener {

    TextInputEditText textinput_id , textinput_pw;
    Button btn_login;
    TextView tv_join, tv_ask ,tv_showTutorial;
    String TAG = "ScheduleLoginActivity";

    RetroBaseApi retroBaseApi;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_login);
        Stetho.initializeWithDefaults(this);

        //쉐어드 값 초기화
//        SharedPreferences pref1 =  getSharedPreferences("Login", MODE_PRIVATE);
//        SharedPreferences.Editor editor1 = pref1.edit();
//        editor1.clear();
//        editor1.apply();
//
//        SharedPreferences pref2 =  getSharedPreferences("login", MODE_PRIVATE);
//        SharedPreferences.Editor editor2 = pref2.edit();
//        editor2.clear();
//        editor2.apply();

//        SharedPreferences pref3 =  getSharedPreferences("LOCAL", MODE_PRIVATE);
//        SharedPreferences.Editor editor3 = pref3.edit();
//        editor3.clear();
//        editor3.apply();
//

        //초기화
        textinput_id = findViewById(R.id.textinput_id);
        textinput_pw = findViewById(R.id.textinput_pw);
        btn_login = findViewById(R.id.btn_login);
        tv_join = findViewById(R.id.tv_join);
        tv_ask = findViewById(R.id.tv_ask);
        tv_showTutorial = findViewById(R.id.tv_showTutorial);

        //클릭 리스너
        tv_join.setOnClickListener(this);
        tv_ask.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_showTutorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //회원가입
            case R.id.tv_join :
                //회원가입 페이지로 이동
                Intent intent = new Intent(ScheduleLoginActivity.this, ScheduleJoinActivity.class);
                startActivity(intent);
                Log.d(TAG, "onClick: 클릭!");
                break;

            //문의하기
            case R.id.tv_ask :
                break;

            //로그인
            case R.id.btn_login :
                //입력한 정보
                String email = textinput_id.getText().toString();
                String password = textinput_pw.getText().toString();

                //서버에 보내서 위의 정보와 일치하는 유저가 있는지 확인.
                checkJoinUser(email, password);


                break;

            //듀토리얼 보기
            case R.id.tv_showTutorial :

                break;


        }
    }



    public void checkJoinUser(String email, String password ){

        Log.d(TAG, "checkJoinUser: 서버에 보내는 email "+ email);
        Log.d(TAG, "checkJoinUser: 서버에 보내는 password "+ password);

        retroBaseApi = retrofit.getRetrofit().create(RetroBaseApi.class);
        retroBaseApi.postLogin(email,password ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //서버에서 보낸 정보를 User 객체로 바꿈
                User user = (User) response.body();

                Log.d(TAG, "onResponse: user 유저 도착 ? "+user);

                if(user.getEmail().isEmpty()){//로그인 실패
                    Toast.makeText(ScheduleLoginActivity.this, R.string.schedule_login_checkJoinUserFalse, Toast.LENGTH_SHORT).show();
                }else{// 로그인 성공

                    //쉐어드에 email과 user 의 idx를 저장.
                    SharedPreferences sharedPreferences = getSharedPreferences("Login",MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("email",user.getEmail());
                    edit.putInt("idx",user.getIdx());
                    edit.apply();

                    //일정짜기 메인 페이지로 이동.
                    Intent intent = new Intent(ScheduleLoginActivity.this, ScheduleNavigationPlanActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //로그인 실패
                Log.d(TAG, "onFailure: "+t.getMessage()) ;
                Toast.makeText(ScheduleLoginActivity.this, R.string.schedule_login_checkJoinUserFalse, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
