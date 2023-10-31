package com.example.onlineexams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private String userUID, firstName;
    private DatabaseReference database;

    private ImageView ivSignOut;
    private TextView tvName, tvTotalQuestions, tvTotalPoints;
    private Button btnStartQuiz, btnCreateQuiz;
    private EditText etCreateQuizText, etStartQuizText;
    private RelativeLayout rlSolvedQuiz, rlYourQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUI();
    }

    private void setUI() {
        database = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressDialog = new ProgressDialog(Home.this);

        progressDialog.setMessage("Loading... ");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();
        userUID = bundle.getString("User UID");

        ivSignOut = findViewById(R.id.ivSignOut);

        rlSolvedQuiz = findViewById(R.id.rlSolvedQuiz);
        rlYourQuizzes = findViewById(R.id.rlYourQuizzes);

        tvName = findViewById(R.id.tvName);
        tvTotalQuestions = findViewById(R.id.tvTotalQuestions);
        tvTotalPoints = findViewById(R.id.tvTotalPoints);

        btnStartQuiz = findViewById(R.id.btnStartQuiz);
        btnCreateQuiz = findViewById(R.id.btnCreateQuiz);

        etCreateQuizText = findViewById(R.id.etCreateQuizText);
        etStartQuizText = findViewById(R.id.etStartQuizText);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot usersRef = snapshot.child("Users").child(userUID);
                firstName = usersRef.child("First Name").getValue().toString();
                if (usersRef.hasChild("Total Points")){
                    String totalPoints = usersRef.child("Total Points").getValue().toString();
                    tvTotalPoints.setText(String.format("%s0d",totalPoints));
                }
                if (usersRef.hasChild("Total Questions")){
                    String totalQuestions = usersRef.child("Total Questions").getValue().toString();
                    tvTotalQuestions.setText(String.format("%s0d",totalQuestions));
                }

                tvName.setText(getString(R.string.welcome_s, firstName));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Can not connect", Toast.LENGTH_SHORT).show();
            }
        };
        database.addListenerForSingleValueEvent(listener);

        ivSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivSignOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}