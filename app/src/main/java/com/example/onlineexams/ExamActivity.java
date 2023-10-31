package com.example.onlineexams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ExamActivity extends AppCompatActivity {
    private Question[] date;
    private String quizID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        setUI();
    }

    private void setUI() {

    }
}