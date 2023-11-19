package com.example.a1_ngame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class OtherSetting extends AppCompatActivity {

    private Button start_game_btn;//게임 시작 버튼
    private String word_length;//단어 길이
    private Button wordTopic_back;//back 버튼

    private String word_val;//firebase에서 랜덤으로 가져온 단어
    private String wordTopic;//설정한 단어 주제

    Random rnd = new Random();
    int num = rnd.nextInt(3);//firebase에서 무작위로 가져오는 index 범위


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_setting);

        start_game_btn=findViewById(R.id.start_game_btn);
        wordTopic_back=findViewById(R.id.wordTopic_back);

        Intent get_intent = getIntent();//날라오는 데이터 값 받기
        wordTopic=get_intent.getStringExtra("wordTopic");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(wordTopic+"_word");//firebase에서 데이터 가져옴

        //단어 개수 선택 박스, Spinner는 콤보박스, 선택박스 의미
        Spinner wordSpinner = (Spinner)findViewById(R.id.word_cnt);
        ArrayAdapter wordAdapter = ArrayAdapter.createFromResource(this, R.array.data_word_cnt, android.R.layout.simple_spinner_item);
        // 드롭다운 클릭 시 선택 창
        wordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어댑터 설정
        wordSpinner.setAdapter(wordAdapter);

        wordSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//단어 개수 선택시
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //ddd.setText((CharSequence) parent.getItemAtPosition(position));
                word_length= (String) parent.getItemAtPosition(position);//
                //데이터 가져오기
                mDatabase.child("cnt_"+word_length).child("id"+num).child("word").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        word_val = dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        start_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent post_intent = new Intent(OtherSetting.this,StartGame.class);//게임 시작 화면으로 이동
                post_intent.putExtra("word_val",word_val);//firebase에서 랜덤으로 가져온 단어 넘겨주기
                startActivity(post_intent);
            }
        });
        wordTopic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_intent=new Intent(OtherSetting.this,WordTopicSetting.class);
                startActivity(back_intent);
            }
        });

    }

}
