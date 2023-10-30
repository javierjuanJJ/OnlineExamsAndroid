package com.example.onlineexams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword1, etPassword2, etFirstName, etLastName;
    private Button btnSignUp;
    private TextView tvLogin;

    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUI();
    }

    private void setUI() {

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        etEmail = findViewById(R.id.etEmail);
        etPassword1 = findViewById(R.id.etPassword1);
        etPassword2 = findViewById(R.id.etPassword2);
        etFirstName = findViewById(R.id.etFirstName);
        etEmail = findViewById(R.id.etEmail);
        etLastName = findViewById(R.id.etLastName);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        btnSignUp.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvLogin:
                Intent i = new Intent(this, SignUpActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.btnSignUp:
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String password1 = etPassword1.getText().toString();
                        String password2 = etPassword2.getText().toString();
                        String email = etEmail.getText().toString();
                        String firstName = etFirstName.getText().toString();

                        if (password1.equals(password2)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    etPassword2.setError("Password do not match");
                                    progressDialog.dismiss();
                                }
                            });
                        }
                        auth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(SignUpActivity.this, (OnCompleteListener<AuthResult>) task -> {
                            if(task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                DatabaseReference ref = database.child("Users").child(user.getUid());
                                ref.child("First Name").setValue(firstName);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SignUpActivity.this, Home.class);
                                        intent.putExtra("User UID", user.getUid());
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Operation failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });

                    }
                });
                thread.start();
                break;
        }
    }
}