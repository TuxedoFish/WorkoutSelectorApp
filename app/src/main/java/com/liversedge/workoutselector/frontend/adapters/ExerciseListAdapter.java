package com.liversedge.workoutselector.frontend.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.utils.ExerciseImageIds;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>{

    private List<WorkoutElement> exercises;
    private Context context;
    private View view;
    private ViewHolder viewHolder;
    private ExerciseImageIds exerciseImageIDs;

    public ExerciseListAdapter(Context context, List<WorkoutElement> exerciseNames) {
        this.exercises = exerciseNames;
        this.context = context;
        this.exerciseImageIDs = new ExerciseImageIds();
    }

    public void setExercises(List<WorkoutElement> exercises) {
        this.exercises = exercises;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView exerciseNameTextView, exerciseLengthTextView;
        private ImageView exerciseImageView;

        public ViewHolder(View v){

            super(v);

            exerciseNameTextView = (TextView) v.findViewById(R.id.exerciseItemText);
            exerciseLengthTextView = (TextView) v.findViewById(R.id.exerciseLengthText);
            exerciseImageView = (ImageView) v.findViewById(R.id.exerciseImage);
        }
    }

    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent,false);

        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        WorkoutElement exercise = exercises.get(position);

        String name = exercise.getName();
        String duration = exercise.getDuration();
        Integer image = exerciseImageIDs.getImageByExerciseName(exercise.getImageName());

        holder.exerciseNameTextView.setText(name);
        holder.exerciseLengthTextView.setText(duration);
        holder.exerciseImageView.setImageResource(image);
    }

    @Override
    public int getItemCount(){
        return exercises.size();
    }
}
