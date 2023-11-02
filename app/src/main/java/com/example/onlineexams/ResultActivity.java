package com.example.onlineexams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.lang.ref.WeakReference;

public class ResultActivity extends AppCompatActivity {
    private Question[] data;
    private String uid, quizUID;

    private ListView rv2;
    private TextView tvTitle, tvTotalPoints;
    private LinearLayout llBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setUI();
    }

    private void setUI() {
        quizUID = getIntent().getStringExtra("Quiz ID");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        rv2 = findViewById(R.id.rv2);
        tvTitle = findViewById(R.id.tvTitle);
        tvTotalPoints = findViewById(R.id.tvTotalPoints);
        llBottom = findViewById(R.id.llBottom);

        if(getIntent().hasExtra("User UID")){
            uid = getIntent().getStringExtra("User ID");
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Quizzes").hasChild(quizUID)){
                    DataSnapshot ansRef = snapshot.child("Quizzes").child(quizUID).child("Answers").child(uid);
                    DataSnapshot qRef = snapshot.child("Quizzes").child(quizUID);
                    tvTitle.setText(qRef.child("Title").getValue().toString());
                    int num = Integer.parseInt(qRef.child("Total Questions").getValue().toString());
                    data = new Question[num];
                    int correctAns = 0;
                    for (int i = 0; i < num; i++) {
                        DataSnapshot qRef2 = qRef.child("Questions").child(String.valueOf(i));
                        Question question = new Question();
                        question.setQuestion(qRef2.child("Question").getValue().toString());
                        question.setOption1(qRef2.child("Question 1").getValue().toString());
                        question.setOption2(qRef2.child("Question 2").getValue().toString());
                        question.setOption3(qRef2.child("Question 3").getValue().toString());
                        question.setOption4(qRef2.child("Question 4").getValue().toString());
                        question.setSelectedAnswer(Integer.parseInt(ansRef.child(String.valueOf(i + 1)).getValue().toString()));
                        int ans = Integer.parseInt(qRef2.child(String.valueOf("Ans")).getValue().toString());
                        if(ans == question.getSelectedAnswer()){
                            correctAns++;
                        }
                        question.setCorrectAnswer(ans);
                        data[i] = question;
                    }
                    tvTotalPoints.setText(  String.format("Total %d/%d", correctAns, data.length));
                    ListAdapterResult listAdapterResult = new ListAdapterResult(data, getApplicationContext());
                    rv2.setAdapter(listAdapterResult);
                }
                else {
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResultActivity.this, "Can not connect", Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.addValueEventListener(listener);
    }
}