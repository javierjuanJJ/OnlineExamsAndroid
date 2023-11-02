package com.example.onlineexams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

   private ArrayList<Question> listAnswers;
   private ViewHolder holder;
   private Context context;
   private int position;

   public CustomAdapter(ArrayList<Question> listAnswers, Context context) {
      this.listAnswers = listAnswers;
      this.context = context;
   }

   @NonNull
   @Override
   public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View inflate = layoutInflater.inflate(R.layout.question_edit, parent, false);
      ViewHolder viewHolder = new ViewHolder(inflate);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
      // holder.setIsRecyclable(false);
      this.holder = holder;
      this.position = position;
      Question question = listAnswers.get(position);
      holder.bind(question,listAnswers, position);

      /*holder.getEtNewExam().setText(question.getQuestion());

      EditText[] listEtEditExams = holder.getListEtEditExams();
      String[] options = question.getOptions();
      for (int counter = 0; counter < options.length; counter++) {
         Log.i("tasksaaaa+", String.valueOf(listEtEditExams[counter]==null));
         Log.i("tasksaaaa+", String.valueOf(counter));
         listEtEditExams[counter].setText(options[counter]);

      }
      int selectedAnswer = question.getCorrectAnswer();

      RadioButton[] listRbEditExams = holder.getListRbEditExams();
      listRbEditExams[selectedAnswer].setChecked(true);

      holder.getEtNewExam().addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
            listAnswers.get(position).setQuestion(editable.toString());
         }
      });


      holder.getEtEditExamOption2().addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
            listAnswers.get(position).setOption2(editable.toString());
         }
      });

      holder.getEtEditExamOption3().addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
            listAnswers.get(position).setOption3(editable.toString());
         }
      });

      holder.getEtEditExamOption4().addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
            listAnswers.get(position).setOption4(editable.toString());
         }
      });

      holder.getEtEditExamOption1().addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
            listAnswers.get(position).setOption1(editable.toString());
         }
      });

      holder.getRgEditExam().setOnCheckedChangeListener(this);


      if (position == listAnswers.size() - 1){
         holder.getEtNewExam().setVisibility(View.VISIBLE);
         holder.getEtNewExam().setOnClickListener(this);
      }*/

   }

   @Override
   public int getItemCount() {
      return listAnswers.size();
   }

   @Override
   public void onCheckedChanged(RadioGroup radioGroup, int i) {
      /*Question question = listAnswers.get(position);
      RadioButton[] listRbEditExams = holder.getListRbEditExams();
      for (int counter = 0; counter < listRbEditExams.length; counter++) {
         if (listRbEditExams[counter].isChecked()){
            question.setCorrectAnswer(counter + 1);
         }
      }*/
   }

   @Override
   public void onClick(View view) {
      /*switch (view.getId()){
         case R.id.etNewExam:
            listAnswers.add(new Question());
            notifyDataSetChanged();
            break;
      }*/
   }

   public class ViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

      private RadioGroup rgEditExam;
      private RadioButton rbEditExamOption1,rbEditExamOption2,rbEditExamOption3,rbEditExamOption4;
      private EditText etEditExamOption1,etEditExamOption2,etEditExamOption3,etEditExamOption4,etNewExam;
      private Question question;
      private ArrayList<Question> listAnswers;
      private int position;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         setUI(itemView);
      }

      private void setUI(View itemView) {
         Log.i("tasksaaaa","is starting");
         rgEditExam = itemView.findViewById(R.id.rgEditExam);
         rbEditExamOption1 = itemView.findViewById(R.id.rbEditExamOption1);
         rbEditExamOption2 = itemView.findViewById(R.id.rbEditExamOption2);
         rbEditExamOption3 = itemView.findViewById(R.id.rbEditExamOption3);
         rbEditExamOption4 = itemView.findViewById(R.id.rbEditExamOption4);

         etEditExamOption1 = itemView.findViewById(R.id.etEditExamOption1);
         Log.i("tasksaaaa", String.valueOf(etEditExamOption1==null));

         etEditExamOption2 = itemView.findViewById(R.id.etEditExamOption2);
         etEditExamOption3 = itemView.findViewById(R.id.etEditExamOption3);
         etEditExamOption4 = itemView.findViewById(R.id.etEditExamOption4);

         etNewExam = itemView.findViewById(R.id.etNewExam);
         
         
      }

      public RadioGroup getRgEditExam() {
         return rgEditExam;
      }

      public RadioButton getRbEditExamOption1() {
         return rbEditExamOption1;
      }

      public RadioButton getRbEditExamOption2() {
         return rbEditExamOption2;
      }

      public RadioButton getRbEditExamOption3() {
         return rbEditExamOption3;
      }

      public RadioButton getRbEditExamOption4() {
         return rbEditExamOption4;
      }

      public EditText getEtEditExamOption1() {
         return etEditExamOption1;
      }

      public EditText getEtEditExamOption2() {
         return etEditExamOption2;
      }

      public EditText getEtEditExamOption3() {
         return etEditExamOption3;
      }

      public EditText getEtEditExamOption4() {
         return etEditExamOption4;
      }

      public EditText getEtNewExam() {
         return etNewExam;
      }

      public EditText[] getListEtEditExams() {
         return new EditText[]{etEditExamOption1, etEditExamOption2, etEditExamOption3, etEditExamOption4};
      }

      public RadioButton[] getListRbEditExams() {
         return new RadioButton[]{rbEditExamOption1, rbEditExamOption2, rbEditExamOption3, rbEditExamOption4};
      }

      public void bind(Question question, ArrayList<Question> listAnswers, int position) {
         getEtNewExam().setText(question.getQuestion());
         this.question = question;
         this.listAnswers = listAnswers;
         this.position = position;

         EditText[] listEtEditExams = getListEtEditExams();
         String[] options = question.getOptions();
         for (int counter = 0; counter < options.length; counter++) {
            Log.i("tasksaaaa+", String.valueOf(listEtEditExams[counter]==null));
            Log.i("tasksaaaa+", String.valueOf(counter));
            listEtEditExams[counter].setText(options[counter]);

         }
         int selectedAnswer = question.getCorrectAnswer();

         RadioButton[] listRbEditExams = getListRbEditExams();
         listRbEditExams[selectedAnswer].setChecked(true);

         getEtNewExam().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               listAnswers.get(position).setQuestion(editable.toString());
            }
         });


         getEtEditExamOption2().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               listAnswers.get(position).setOption2(editable.toString());
            }
         });

         getEtEditExamOption3().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               listAnswers.get(position).setOption3(editable.toString());
            }
         });

         getEtEditExamOption4().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               listAnswers.get(position).setOption4(editable.toString());
            }
         });

         getEtEditExamOption1().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               listAnswers.get(position).setOption1(editable.toString());
            }
         });

         getRgEditExam().setOnCheckedChangeListener(this);


         if (position == listAnswers.size() - 1){
            getEtNewExam().setVisibility(View.VISIBLE);
            getEtNewExam().setOnClickListener(this);
         }
      }

      @Override
      public void onCheckedChanged(RadioGroup radioGroup, int i) {
         Question question = this.question;
         RadioButton[] listRbEditExams = getListRbEditExams();
         for (int counter = 0; counter < listRbEditExams.length; counter++) {
            if (listRbEditExams[counter].isChecked()){
               question.setCorrectAnswer(counter + 1);
            }
         }
      }

      @Override
      public void onClick(View view) {
         switch (view.getId()){
            case R.id.etNewExam:
               listAnswers.add(new Question());
               // notifyDataSetChanged();
               break;
         }
      }
   }
}
