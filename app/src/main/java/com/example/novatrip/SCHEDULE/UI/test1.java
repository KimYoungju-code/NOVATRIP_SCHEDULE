package com.example.novatrip.SCHEDULE.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.novatrip.R;

public class test1 extends AppCompatActivity {
String TAG = "test1";
Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), test2.class);
                startActivityForResult(intent,1);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data !=null){
            Log.d(TAG, "onActivityResult: 인텐트 전달바음 ");
            Log.d(TAG, "onActivityResult: data.getStringExtra(\"test2\");"+data.getStringExtra("test2"));
        }
    }
}
