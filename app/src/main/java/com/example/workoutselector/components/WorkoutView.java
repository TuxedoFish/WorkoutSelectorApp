package com.example.workoutselector.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutselector.R;

import java.util.List;

public class WorkoutView extends ConstraintLayout {

    // Text components
    private TextView titleText, authorText;

    // List of exercises
    private RecyclerView exercises;
    private LinearLayoutManager exercisesLayoutManager;
    private ExerciseListAdapter exercisesListAdapter;

    // Saved for updates
    Context context;

    public WorkoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        this.context = context;

        inflate(context, R.layout.workout_description, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WorkoutView);

        CharSequence title = typedArray.getText(R.styleable.WorkoutView_titleText);
        CharSequence author = typedArray.getText(R.styleable.WorkoutView_authorText);

        typedArray.recycle();

        initComponents();

        setAuthor(author);
        setTitle(title);

    }

    // Updates the adapter
    public void update(WorkoutDAO workout) {
        exercisesListAdapter.setExcerciseNames(workout.getExercises());

        setAuthor(workout.getAuthor());
        setTitle(workout.getTitle());
    }

    private void initComponents() {
        titleText = (TextView) findViewById(R.id.titleText);
        authorText = (TextView) findViewById(R.id.authorText);
        exercises = (RecyclerView) findViewById(R.id.exercisesList);

        initExercisesList();
    }

    private void initExercisesList() {
        exercisesLayoutManager = new LinearLayoutManager(context);
        exercises.setLayoutManager(exercisesLayoutManager);

        exercisesListAdapter = new ExerciseListAdapter(context, new String[] {});
        exercises.setAdapter(exercisesListAdapter);
    }

    private void setTitle(CharSequence title) {
        titleText.setText(title);
    }

    private void setAuthor(CharSequence author) {
        authorText.setText(author);
    }

}
