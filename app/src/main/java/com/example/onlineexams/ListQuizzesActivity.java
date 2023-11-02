package com.example.onlineexams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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

public class ListQuizzesActivity extends AppCompatActivity {
    private boolean showGrade, solvedQuizzes;
    private String oper, uid;
    private TextView tvTitle;
    private ListView rv2;
    private ArrayList<String> ids;
    private DatabaseReference database;
    private boolean createdQuizzes,quizzeGrade;
    private String quizId;
    private ArrayList<String> grades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_quizzes);
        setUI();
    }

    private void setUI() {
        oper = getIntent().getStringExtra("Operation");
        tvTitle = findViewById(R.id.tvTitle);
        rv2 = findViewById(R.id.rv2);

        if (oper.equals("List Solved Quizzes")){
            showGrade = false;
            solvedQuizzes = true;
        } else if (oper.equals("List Created Quizzes")) {
            showGrade = false;
            createdQuizzes = true;
        }
        else if (oper.equals("List Quiz Grades")) {
            quizId = getIntent().getStringExtra("Quiz Id");
            tvTitle.setText(quizId);
            quizzeGrade = true;
            showGrade = true;
            tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Quiz Id", quizId);
                    clipboardManager.setPrimaryClip(clip);
                    return true;
                }
            });


        }
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ids = new ArrayList<>();
        grades = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference();

        if(solvedQuizzes){
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DataSnapshot ds = snapshot.child("Users").child(uid).child("Quizzes solved");
                    for (DataSnapshot d : ds.getChildren()) {
                        ids.add(d.getKey());
                        data.add(snapshot.child("Quizzes").child(d.getKey()).child("Title").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ListQuizzesActivity.this, "Can not connect", Toast.LENGTH_SHORT).show();
                }
            };
            database.addValueEventListener(listener);
        } else if (createdQuizzes) {
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DataSnapshot ds = snapshot.child("Users").child(uid).child("Quizzes Created");
                    for (DataSnapshot d : ds.getChildren()) {
                        ids.add(d.getKey());
                        data.add(snapshot.child("Quizzes").child(d.getKey()).child("Title").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ListQuizzesActivity.this, "Can not connect", Toast.LENGTH_SHORT).show();
                }
            };
            database.addValueEventListener(listener);
        }

        else if (quizzeGrade) {
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DataSnapshot ds = snapshot.child("Users").child(quizId).child("Answers");
                    for (DataSnapshot d : ds.getChildren()) {
                        ids.add(d.getKey());
                        String firstName = snapshot.child("Users").child(d.getKey()).child("First Name").getValue().toString();
                        String lastName = snapshot.child("Users").child(d.getKey()).child("Last Name").getValue().toString();
                        data.add(firstName + " " + lastName);
                        String points = snapshot.child("Quizzes").child(quizId).child("Answers").child(d.getKey()).child("Points").getValue().toString();
                        String total = snapshot.child("Quizzes").child(quizId).child("Total Questions").getValue().toString();
                        grades.add(points +"/"+total);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ListQuizzesActivity.this, "Can not connect", Toast.LENGTH_SHORT).show();
                }
            };
            database.addValueEventListener(listener);
        }
        ListAdapterQuizzes listAdapterQuizzes = new ListAdapterQuizzes(data, ids, getApplicationContext(), showGrade, solvedQuizzes, createdQuizzes, quizzeGrade, quizId);
        rv2.setAdapter(listAdapterQuizzes);
    }
}