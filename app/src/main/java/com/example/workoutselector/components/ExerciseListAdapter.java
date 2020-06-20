package com.example.workoutselector.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutselector.R;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>{

    String[] excerciseNames;
    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView;

    public ExerciseListAdapter(Context context, String[] exerciseNames){
        this.excerciseNames = exerciseNames;
        this.context = context;
    }

    public void setExcerciseNames(String[] exerciseNames) {
        this.excerciseNames = exerciseNames;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(View v){

            super(v);

            textView = (TextView) v.findViewById(R.id.exerciseItemText);
        }
    }

    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view1 = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent,false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.textView.setText(excerciseNames[position]);
    }

    @Override
    public int getItemCount(){
        return excerciseNames.length;
    }
}
