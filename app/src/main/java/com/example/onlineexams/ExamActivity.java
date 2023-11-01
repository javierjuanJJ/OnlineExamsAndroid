package com.example.onlineexams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ExamActivity extends AppCompatActivity {
    private Question[] date;

    private ListView rv2;
    private TextView tvTitle;
    private LinearLayout llBottom;


    private DatabaseReference database;

    private String uid;
    private int oldTotalPoints = 0, newTotalPoints = 0;
    private String quizId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        setUI();
    }

    private void setUI() {
        rv2 = findViewById(R.id.rv2);
        tvTitle = findViewById(R.id.tvTitle);
        llBottom = findViewById(R.id.llBottom);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance().getReference();
        quizId = getIntent().getStringExtra("Quiz ID");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Quizzes").hasChild("Last ID")){
                    DataSnapshot quizzes = snapshot.child("Quizzes").child(quizId);
                    tvTitle.setText(quizzes.child("Title").getValue().toString());
                    int totalQuestions = Integer.parseInt(quizzes.child("Total Questions").getValue().toString());
                    date = new Question[totalQuestions];
                    for (int counter = 0; counter < totalQuestions; counter++) {
                        DataSnapshot qRef = quizzes.child("Questions").child(String.valueOf(counter));
                        Question question = new Question();
                        question.setQuestion(qRef.child("Question").getValue().toString());
                        question.setOption1(qRef.child("Option 1").getValue().toString());
                        question.setOption2(qRef.child("Option 2").getValue().toString());
                        question.setOption3(qRef.child("Option 3").getValue().toString());
                        question.setOption4(qRef.child("Option 4").getValue().toString());

                        int ans = Integer.parseInt(qRef.child("Ans").getValue().toString());
                        question.setCorrectAnswer(ans);
                        date[counter] = question;
                    }
                    ListAdapter listAdapter = new ListAdapter(date, getApplicationContext());
                    rv2.setAdapter(listAdapter);
                    DataSnapshot ref2 = snapshot.child("Users").child(uid);
                    if (ref2.hasChild("Total Points")){
                        oldTotalPoints = Integer.parseInt(ref2.child("Total Points").getValue().toString());
                    }
                    else if (ref2.hasChild("Total Questions")){
                        newTotalPoints = Integer.parseInt(ref2.child("Total Questions").getValue().toString());
                    }
                }
                else {
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ExamActivity.this, "Can not connect", Toast.LENGTH_SHORT).show();
            }
        };


        database.addValueEventListener(listener);

    }
}