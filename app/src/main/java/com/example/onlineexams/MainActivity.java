package com.example.onlineexams;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();

    }

    private void setUI() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent i = new Intent(this, Home.class);
            i.putExtra("User UID", user.getUid());
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSignUp:
                Intent i = new Intent(this, SignUpActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btnLogin:
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Thread thread = new Thread(() -> {
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, task -> {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Intent i = new Intent(MainActivity.this, Home.class);
                                    i.putExtra("User UID", user.getUid());
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                        else {
                            Toast.makeText(this, "Operation failed.", Toast.LENGTH_SHORT).show();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    });
                });
                thread.start();

                break;
        }
    }
}