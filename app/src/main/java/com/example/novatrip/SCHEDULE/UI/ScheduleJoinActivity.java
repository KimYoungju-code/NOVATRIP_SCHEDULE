package com.example.novatrip.SCHEDULE.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novatrip.R;
import com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi;
import com.google.android.material.textfield.TextInputEditText;


/**
 * 회원가입
 **/
public class ScheduleJoinActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText textinput_joinEmail, textinput_joinPw, textinput_RejoinPw;
    TextView tv_joinOk;
    ImageView iv_back , im_check_password;

    Boolean check_password = false;// 비밀번호와 다시 입력한 비밀번호가 일치하는지 확인.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_join);

        //초기화
        textinput_joinEmail = findViewById(R.id.textinput_joinEmail);
        textinput_joinPw = findViewById(R.id.textinput_joinPw);
        textinput_RejoinPw = findViewById(R.id.textinput_RejoinPw);
        tv_joinOk = findViewById(R.id.tv_joinOk);
        iv_back = findViewById(R.id.iv_back);
        im_check_password = findViewById(R.id.im_check_password);//비밀번호 일치 확인


        //클릭 리스너
        tv_joinOk.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        //비밀번호와 다시 입력한 비밀번호가 일치하는지 여부 확인.
        textinput_RejoinPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(textinput_joinPw.getText().toString().equals(textinput_RejoinPw.getText().toString())) {
                    im_check_password.setImageResource(R.drawable.tick);
                    check_password= true;
                } else {
                    im_check_password.setImageResource(R.drawable.close);
                    check_password= false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //회원가입하기
            case R.id.tv_joinOk:

//                if(!check_password ){
//                    Toast.makeText(this, R.string.schedule_login_checkEnteredInformationAgain, Toast.LENGTH_SHORT).show();
//                }else{
//
//                }

                break;

            //뒤로가기
            case R.id.iv_back:

                break;
            }
        }
    }

