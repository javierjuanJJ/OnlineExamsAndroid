package com.example.onlineexams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
   private Question[] listQuestions;
   private Context context;

   private TextView tvQuestion, tvCorrectAnswer;
   private RadioButton rbOption1,rbOption2,rbOption3,rbOption4;
   private RadioGroup rgOptions;
   private int counterActually;
   private RadioButton[] radioButtons = {rbOption1,rbOption2,rbOption3,rbOption4};

   public ListAdapter(Question[] listQuestions, Context context) {
      this.listQuestions = listQuestions;
      this.context = context;
   }

   public Context getContext() {
      return context;
   }

   public Question[] getListQuestions() {
      return listQuestions;
   }

   @Override
   public int getCount() {
      return getListQuestions().length;
   }

   @Override
   public Object getItem(int i) {
      return getListQuestions()[i];
   }

   @Override
   public long getItemId(int i) {
      return i;
   }

   @Override
   public View getView(int i, View view, ViewGroup viewGroup) {
      LayoutInflater inflater = LayoutInflater.from(getContext());
      View v = inflater.inflate(R.layout.question, null);
      counterActually = i;
      tvQuestion = v.findViewById(R.id.tvQuestion);
      tvCorrectAnswer = v.findViewById(R.id.tvCorrectAnswer);

      rbOption1 = v.findViewById(R.id.rbOption1);
      rbOption2 = v.findViewById(R.id.rbOption2);
      rbOption3 = v.findViewById(R.id.rbOption3);
      rbOption4 = v.findViewById(R.id.rbOption4);

      rgOptions = v.findViewById(R.id.rgOptions);

      tvQuestion.setText(listQuestions[i].getQuestion());

      rbOption1.setText(listQuestions[i].getOption1());
      rbOption2.setText(listQuestions[i].getOption2());
      rbOption3.setText(listQuestions[i].getOption3());
      rbOption4.setText(listQuestions[i].getOption4());

      rbOption1.setOnCheckedChangeListener(this);
      rbOption2.setOnCheckedChangeListener(this);
      rbOption3.setOnCheckedChangeListener(this);
      rbOption4.setOnCheckedChangeListener(this);


      return v;
   }

   @Override
   public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
      if (compoundButton.getId() != View.NO_ID && b){
         listQuestions[counterActually].setSelectedAnswer(counterActually + 1);
         radioButtons[counterActually].setChecked(true);
      }
   }
}
