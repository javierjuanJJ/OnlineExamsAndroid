package com.example.onlineexams;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ListAdapterQuizzes extends BaseAdapter {
   private ArrayList<String> listQuestions, ids;
   private Context context;

   private boolean showGrade, solvedQuizzes;
   private int counterActually;
   private TextView tvGrade;
   private TextView tvQuiz;
   private RelativeLayout rlItem;
   private boolean createdQuizzes;
   private boolean quizGrades;
   private String quizId;


   public ListAdapterQuizzes(ArrayList<String> listQuestions, ArrayList<String> ids, Context context, boolean showGrade, boolean solvedQuizzes, boolean createdQuizzes, boolean quizGrades, String quizId) {
      this.listQuestions = listQuestions;
      this.ids = ids;
      this.context = context;
      this.showGrade = showGrade;
      this.solvedQuizzes = solvedQuizzes;
      this.createdQuizzes = createdQuizzes;
      this.quizGrades = quizGrades;
      this.quizId = quizId;
   }

   public Context getContext() {
      return context;
   }

   public ArrayList<String> getListQuestions() {
      return listQuestions;
   }

   @Override
   public int getCount() {
      return getListQuestions().size();
   }

   @Override
   public Object getItem(int i) {
      return getListQuestions().get(i);
   }

   @Override
   public long getItemId(int i) {
      return i;
   }

   @Override
   public View getView(int i, View view, ViewGroup viewGroup) {
      LayoutInflater inflater = LayoutInflater.from(getContext());
      View v = inflater.inflate(R.layout.quizzes_listitem, null);
      counterActually = i;


      tvGrade = v.findViewById(R.id.tvGrade);
      tvQuiz = v.findViewById(R.id.tvQuiz);
      rlItem = v.findViewById(R.id.rlItem);
      tvQuiz.setText(listQuestions.get(i));


      tvGrade.setVisibility(showGrade ? View.VISIBLE : View.GONE);

      if (solvedQuizzes){
         rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, ResultActivity.class);
               intent.putExtra("Quiz ID", listQuestions.get(i));
               context.startActivity(intent);
            }
         });
      } else if (createdQuizzes) {

         rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, ListQuizzesActivity.class);
               intent.putExtra("Quiz ID", ids.get(i));
               intent.putExtra("Operation", "List Quiz Grades");
               intent.putExtra("Quiz Title", listQuestions.get(i));


               context.startActivity(intent);
            }
         });
      }
      else if (quizGrades) {
         tvGrade.setText(listQuestions.get(i));
         rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, ResultActivity.class);
               intent.putExtra("Quiz ID", quizId);
               intent.putExtra("User UID", ids.get(i));
               context.startActivity(intent);
            }
         });
      }
      return v;
   }
}
