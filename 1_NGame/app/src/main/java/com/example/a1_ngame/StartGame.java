package com.example.a1_ngame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartGame extends AppCompatActivity {


    private TextView topic_text;//주제 텍스트
    private TextView word_text;//단어 텍스트
    private TextView time_count;//시간 측정
    private  TextView n_word_Btn;//1/n행시 버튼
    private TextView alert_text;//알림 텍스트

    private String topic_val;//firebase에서 가져온 주제
    private String word_val;//firebase에서 가져온 단어

    private int index = 0;//substring에 사용할 index
    private int i = 1;//


    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("topic");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);

        Intent get_intent = getIntent();//날라오는 데이터 값 받기
        word_val=get_intent.getStringExtra("word_val");


        time_count=findViewById(R.id.time_count);
        n_word_Btn=findViewById(R.id.n_word_Btn);
        alert_text= findViewById(R.id.alert_text);
        topic_text=findViewById(R.id.topic_text);
        word_text=findViewById(R.id.word_text);


        word_text.setText("단어: "+word_val);

        n_word_Btn.setText(word_val.substring(index, i));
        index++;
        i++;

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                topic_val = dataSnapshot.getValue(String.class);
                topic_text.setText("주제: "+topic_val);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent post_intent=new Intent(StartGame.this, MainActivity.class);//시작화면으로 이동
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {//시간 측정 1000이 1초를 의미
            public void onTick(long millisUntilFinished) {//시간 측정
                int num = (int) (millisUntilFinished / 1000);
                time_count.setText(Integer.toString(num + 1));
            }

            public void onFinish() {//시간 측정 끝난 경우
                time_count.setText("끝");
                n_word_Btn.setText("시간 초과");
                alert_text.setText("클릭시 시작화면으로 넘어갑니다");
                n_word_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(post_intent);
                    }
                });

            }
        }.start();

        n_word_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i < word_val.length()+1) {
                    n_word_Btn.setText(word_val.substring(index, i));
                    i++;
                    index++;
                    countDownTimer.start();//timer 새로 시작
                }else{
                    countDownTimer.cancel();//timer 작동 멈춤
                    time_count.setText("끝");
                    n_word_Btn.setText("성공");
                    alert_text.setText("클릭시 시작화면으로 넘어갑니다");
                    n_word_Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(post_intent);
                        }
                    });
                }
            }
        });
    }





}
