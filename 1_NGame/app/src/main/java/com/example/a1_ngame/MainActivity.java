package com.example.a1_ngame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button start_btn;//1/n행시 게임 스타트 버튼
    private Button start_img_btn;//이미지 게임 스타트 버튼

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_btn=findViewById(R.id.start_btn);
        start_img_btn=findViewById(R.id.start_img_btn);

        Intent post_intent=new Intent(MainActivity.this,TopicSetting.class);//1/n행시 게임 주제 설정 화면으로 이동
        Intent post_intent_img=new Intent(MainActivity.this,OtherSettingImg.class);//이미지 게임 이미지 개수 설정 화면으로 이동
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(post_intent);
            }
        });

        start_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(post_intent_img);
            }
        });
    }



}