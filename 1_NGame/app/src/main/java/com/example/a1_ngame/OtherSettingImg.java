package com.example.a1_ngame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class OtherSettingImg extends AppCompatActivity {


    private Button img_start_btn;
    private String img_cnt;
    private Button Topic_back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_setting_img);

        img_start_btn=findViewById(R.id.img_start_btn);
        Topic_back=findViewById(R.id.Topic_back);

        Spinner wordSpinner = (Spinner)findViewById(R.id.img_cnt);
        ArrayAdapter wordAdapter = ArrayAdapter.createFromResource(this, R.array.data_word_cnt, android.R.layout.simple_spinner_item);
        // 드롭다운 클릭 시 선택 창
        wordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어댑터 설정
        wordSpinner.setAdapter(wordAdapter);

        wordSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//이미지 개수 선택 시
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                img_cnt= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        img_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent post_intent = new Intent(OtherSettingImg.this,StartGameImg.class);//이미지 게임 시작 화면으로 이동
                post_intent.putExtra("img_cnt",img_cnt);//이미지 개수 넘겨주기
                startActivity(post_intent);
            }
        });

        Topic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_intent=new Intent(OtherSettingImg.this,MainActivity.class);
                startActivity(back_intent);
            }
        });


    }
}
