package com.example.a1_ngame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TopicSetting extends AppCompatActivity {

    private Button friend_btn;
    private Button love_btn;
    private Button comedy_btn;
    private Button thriller_btn;
    private Button home_back;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("topic");//주제 값 전달

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면에 보여질 뷰 설정
        setContentView(R.layout.topic_setting);

        friend_btn=findViewById(R.id.friend_btn);
        love_btn=findViewById(R.id.love_btn);
        comedy_btn=findViewById(R.id.comedy_btn);
        thriller_btn=findViewById(R.id.thriller_btn);
        home_back=findViewById(R.id.home_back);
    }

    @Override
    public void onStart(){
        super.onStart();


        Intent post_intent=new Intent(TopicSetting.this, WordTopicSetting.class);

        friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionRef.setValue(friend_btn.getText().toString());//주제 친구로 설정
                    startActivity(post_intent);
            }
        });

        love_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionRef.setValue(love_btn.getText().toString());//주제 사랑으로 설정
                    startActivity(post_intent);

            }
        });
        comedy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionRef.setValue(comedy_btn.getText().toString());//주제 코미디로 설정
                    startActivity(post_intent);

            }
        });
        thriller_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionRef.setValue(thriller_btn.getText().toString());//주제 스릴러로 설정
                    startActivity(post_intent);
            }
        });
        home_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_intent=new Intent(TopicSetting.this,MainActivity.class);
                startActivity(back_intent);
            }
        });
    }
}
