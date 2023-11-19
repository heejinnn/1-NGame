package com.example.a1_ngame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class WordTopicSetting extends AppCompatActivity {

    private Button objects_btn;
    private Button animal_btn;
    private Button person_btn;
    private Button sports_btn;
    private Button movie_btn;
    private Button drama_btn;
    private Button topic_back;

    private String wordTopic; //선택한 단어 주제

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("word_topic");//단어 주제 전송

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_topic_setting);

        objects_btn=findViewById(R.id.objects_btn);
        animal_btn=findViewById(R.id.animal_btn);
        person_btn=findViewById(R.id.person_btn);
        sports_btn=findViewById(R.id.sports_btn);
        movie_btn=findViewById(R.id.movie_btn);
        drama_btn=findViewById(R.id.drama_btn);
        topic_back=findViewById(R.id.topic_back);

        Intent post_intent= new Intent(WordTopicSetting.this,OtherSetting.class);

        objects_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionRef.setValue(objects_btn.getText().toString());//단어 주제 '사물' 보내기
                wordTopic="objects";
                post_intent.putExtra("wordTopic",wordTopic);
                startActivity(post_intent);
            }
        });

        animal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionRef.setValue(animal_btn.getText().toString());//단어 주제 '동물' 보내기
                wordTopic="animal";
                post_intent.putExtra("wordTopic",wordTopic);
                startActivity(post_intent);
            }
        });
        person_btn.setOnClickListener(new View.OnClickListener() {//단어 주제 '인물' 보내기
            @Override
            public void onClick(View view) {
                conditionRef.setValue(person_btn.getText().toString());
                wordTopic="person";
                post_intent.putExtra("wordTopic",wordTopic);
                startActivity(post_intent);
            }
        });
        sports_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionRef.setValue(sports_btn.getText().toString());//단어 주제 '스포츠' 보내기
                wordTopic="sports";
                post_intent.putExtra("wordTopic",wordTopic);
                startActivity(post_intent);
            }
        });
        movie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionRef.setValue(movie_btn.getText().toString());//단어 주제 '영화' 보내기
                wordTopic="movie";
                post_intent.putExtra("wordTopic",wordTopic);
                startActivity(post_intent);
            }
        });
        drama_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionRef.setValue(drama_btn.getText().toString());//단어 주제 '드라마' 보내기
                wordTopic="drama";
                post_intent.putExtra("wordTopic",wordTopic);
                startActivity(post_intent);
            }
        });
        topic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_intent=new Intent(WordTopicSetting.this,TopicSetting.class);
                startActivity(back_intent);
            }
        });
    }

}
