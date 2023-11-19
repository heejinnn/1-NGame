package com.example.a1_ngame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartGameImg extends AppCompatActivity {

    private TextView time_count_img;//시간 측정
    private ImageButton img_Btn;//이미지 나오는 버튼
    private TextView alert_text_img;//알림 텍스트
    private TextView img_answer;//이미지 답

    private String img_cnt;//이미지 개수

    private int index=1;//firebase에서 가져온 index

    Random r = new Random();
    List<Integer> list = new ArrayList<>();//랜덤 숫자 저장

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("img_data");//이미지 답 불러오기
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://ngame-project-e7402.appspot.com");//firebase storage 사용
    StorageReference storageRef = storage.getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game_img);

        Intent get_intent= getIntent();
        img_cnt=get_intent.getStringExtra("img_cnt");//이미지 개수 받아오기


        int a[] = new int[Integer.parseInt(img_cnt)];

        //랜덤 숫자 중복 없애기
        for(int i=0;i< a.length;i++)
        {
            a[i] = r.nextInt(7);
            for(int j=0;j<i;j++)
            {
                if(a[i]==a[j])
                {
                    i--;
                }
            }
        }

        for (int val : a) {
            list.add(val);
        }

        time_count_img=findViewById(R.id.time_count_img);
        img_Btn=findViewById(R.id.img_Btn);
        alert_text_img= findViewById(R.id.alert_text_img);
        img_answer=findViewById(R.id.img_answer);

        storageRef.child("img_data_box/img"+list.get(0)+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//이미지 초기 값 설정
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(img_Btn);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        Intent post_intent=new Intent(StartGameImg.this, MainActivity.class);//시작 화면으로 이동
        CountDownTimer countDownTimer = new CountDownTimer(6000, 1000) {//timer 작동
            public void onTick(long millisUntilFinished) {//시간 측정
                int num = (int) (millisUntilFinished / 1000);
                time_count_img.setText(Integer.toString(num + 1));
            }

            public void onFinish() {//시간 측정 끝난 경우
                time_count_img.setText("시간 초과");
                alert_text_img.setText("클릭시 시작화면으로 넘어갑니다");
                mDatabase.child("img"+list.get(index-1)).child("value").addValueEventListener(new ValueEventListener() {//이미지 답 표시
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        img_answer.setText(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                img_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(post_intent);
                    }
                });

            }
        }.start();

        img_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index < Integer.parseInt(img_cnt)){//index가 이미지 개수보다 작을 때 까지
                    countDownTimer.start();
                    storageRef.child("img_data_box/img"+list.get(index)+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //이미지 로드 성공시
                            Glide.with(getApplicationContext())
                                    .load(uri)
                                    .into(img_Btn);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //이미지 로드 실패시
                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                    index++;
                }
                else{
                    countDownTimer.cancel();
                    time_count_img.setText("끝");
                    alert_text_img.setText("클릭시 시작화면으로 넘어갑니다");
                    img_Btn.setOnClickListener(new View.OnClickListener() {
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
