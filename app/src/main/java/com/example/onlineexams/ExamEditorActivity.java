package com.example.onlineexams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ExamEditorActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Question> listQuestions;
    private RecyclerView rv;
    private int quizId;
/*    private String quizIdString;*/
    private String quizTitle;
    private Button btnSubmit;
    private DatabaseReference database;
    private String quizID;
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int adapterPosition = target.getAdapterPosition();
            int positionTarget = viewHolder.getAdapterPosition();
            Collections.swap(listQuestions, adapterPosition, positionTarget);
            rv.getAdapter().notifyItemMoved(adapterPosition, positionTarget);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_editor);
        setUI();
    }

    private void setUI() {
        listQuestions = new ArrayList<>();
        rv = findViewById(R.id.rv);
        database = FirebaseDatabase.getInstance().getReference();
        btnSubmit = findViewById(R.id.btnSubmit);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Quizzes").hasChild("Last ID")){
                    String lID = snapshot.child("Quizzes").child("Last ID").getValue().toString();
                    quizId = Integer.parseInt(lID);
                }
                else {
                    quizId = 100000;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ExamEditorActivity.this, "Can not connect", Toast.LENGTH_SHORT).show();
            }
        };

        rv.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv);

        listQuestions = new ArrayList<>();
        listQuestions.add(new Question());

        rv.setAdapter(new CustomAdapter(listQuestions));
        btnSubmit.setOnClickListener(this);
        quizTitle = getIntent().getExtras().getString("Quiz Title", "");
        /*quizIdString = getIntent().getExtras().getString("Quiz ID", "");*/
        database.addValueEventListener(listener);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                DatabaseReference reference = database.child("Quizzes");
                reference.child(String.valueOf(quizId)).child("Title").setValue(quizTitle);
                reference.child(String.valueOf(quizId)).child("Total Questions").setValue(listQuestions.size());
                DatabaseReference reference2 = database.child(String.valueOf(quizId)).child("Questions");

                for (int counter = 0; counter < listQuestions.size(); counter++) {

                    String pathString = String.valueOf(counter);
                    reference2.child(pathString).child("Question").setValue(listQuestions.get(counter).getQuestion());
                    reference2.child(pathString).child("Option 1").setValue(listQuestions.get(counter).getOption1());
                    reference2.child(pathString).child("Option 2").setValue(listQuestions.get(counter).getOption2());
                    reference2.child(pathString).child("Option 3").setValue(listQuestions.get(counter).getOption3());
                    reference2.child(pathString).child("Option 4").setValue(listQuestions.get(counter).getOption4());
                    reference2.child(pathString).child("Ans").setValue(listQuestions.get(counter).getCorrectAnswer());
                }

                database.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Quizzes Created").child(String.valueOf(quizId)).setValue("");
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Quiz Id", String.valueOf(quizId));
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(this, "Your quiz id: " + quizID + " copied to clipboard", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}