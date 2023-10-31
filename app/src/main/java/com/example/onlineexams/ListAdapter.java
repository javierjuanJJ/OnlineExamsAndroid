package com.example.onlineexams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapter extends BaseAdapter {
   private Question[] listQuestions;
   private Context context;
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
      View v = inflater.inflate(R.layout.question, viewGroup, false);

      return v;
   }
}
