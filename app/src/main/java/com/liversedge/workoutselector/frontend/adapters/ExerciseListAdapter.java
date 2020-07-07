package com.liversedge.workoutselector.frontend.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;

import com.liversedge.workoutselector.frontend.activity.ExerciseActivity;
import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.utils.ExerciseImageIds;
import com.liversedge.workoutselector.utils.ImageHelper;

import java.util.List;

import static com.liversedge.workoutselector.utils.Constants.INTENT_EXERCISE_ID;
import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>{

    private List<WorkoutElement> exercises;
    private Context context;
    private View view;
    private ViewHolder viewHolder;
    private ExerciseImageIds exerciseImageIDs;
    private int workoutID;

    public ExerciseListAdapter(Context context, List<WorkoutElement> exerciseNames, int workoutID) {
        this.exercises = exerciseNames;
        this.context = context;
        this.exerciseImageIDs = new ExerciseImageIds();
        this.workoutID = workoutID;
    }

    public void setExercises(List<WorkoutElement> exercises) {
        this.exercises = exercises;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView exerciseNameTextView, exerciseLengthTextView;
        private ImageView exerciseImageView, arrowImageView;
        private ConstraintLayout exerciseItemHolder;

        public ViewHolder(View v){

            super(v);

            arrowImageView = (ImageView) v.findViewById(R.id.rightArrowImage);
            exerciseItemHolder = (ConstraintLayout) v.findViewById(R.id.exerciseItemHolder);
            exerciseNameTextView = (TextView) v.findViewById(R.id.exerciseItemText);
            exerciseLengthTextView = (TextView) v.findViewById(R.id.exerciseLengthText);
            exerciseImageView = (ImageView) v.findViewById(R.id.exerciseImageLargeExerciseItem);
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
        final WorkoutElement exercise = exercises.get(position);

        String name = exercise.getName();
        String duration = exercise.getDuration();
        Integer image = exerciseImageIDs.getImageByExerciseName(exercise.getImageName());

        holder.exerciseNameTextView.setText(name);
        holder.exerciseLengthTextView.setText(duration);

        RoundedBitmapDrawable exerciseIconDrawable = ImageHelper.getRoundedImage(image, 100, context);
        holder.exerciseImageView.setImageDrawable(exerciseIconDrawable);

        if(exercise.shouldCenter()) {
            /**
             * Description item
             */
            // Remove other elements
            holder.arrowImageView.setVisibility(View.GONE);
            holder.exerciseImageView.setVisibility(View.GONE);
            holder.exerciseLengthTextView.setVisibility(View.GONE);
            // Center text
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.exerciseNameTextView.getLayoutParams();
            params.horizontalBias = 0.5f;
            holder.exerciseNameTextView.setLayoutParams(params);
            holder.exerciseNameTextView.setGravity(Gravity.CENTER);
            // Make text bold
            holder.exerciseNameTextView.setTypeface(null, Typeface.BOLD);
            holder.exerciseNameTextView.setTextColor(context.getResources().getColor(R.color.textColor));
            // Change text size
            holder.exerciseNameTextView.setTextSize(18);
        } else {
            /**
             * Exercise item
             */
            // Remove other elements
            holder.arrowImageView.setVisibility(View.VISIBLE);
            holder.exerciseImageView.setVisibility(View.VISIBLE);
            holder.exerciseLengthTextView.setVisibility(View.VISIBLE);
            // Clear constraints
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.exerciseNameTextView.getLayoutParams();
            params.horizontalBias = 0.0f;
            holder.exerciseNameTextView.setLayoutParams(params);
            holder.exerciseNameTextView.setGravity(Gravity.LEFT);
            // Make text normal
            holder.exerciseNameTextView.setTypeface(null, Typeface.NORMAL);
            // Normal size
            holder.exerciseNameTextView.setTextSize(14);
            holder.exerciseNameTextView.setTextColor(context.getResources().getColor(R.color.textColor));

            // If the exercise is clicked take to the screen describing the exercise
            holder.exerciseItemHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open the ExerciseActivity
                    Intent intent = new Intent(context, ExerciseActivity.class);

                    // Pass in information about the current workout and exercises
                    intent.putExtra(INTENT_EXERCISE_ID, exercise.getID());
                    intent.putExtra(INTENT_WORKOUT_ID, workoutID);

                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return exercises.size();
    }
}
