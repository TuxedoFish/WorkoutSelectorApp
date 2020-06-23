package com.example.workoutselector.frontend.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutselector.R;
import com.example.workoutselector.backend.firebase.WorkoutDAO;
import com.example.workoutselector.frontend.adapters.Exercise;
import com.example.workoutselector.frontend.adapters.ExerciseListAdapter;

import java.util.ArrayList;

public class WorkoutView extends ConstraintLayout {

    // Text components
    private TextView titleText, authorText, repetitionsText;

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
        CharSequence repetitions = typedArray.getText(R.styleable.WorkoutView_repetitionsText);

        typedArray.recycle();

        initComponents();

        setAuthor(author);
        setTitle(title);
        setRepetitions(repetitions);
    }

    // Updates the adapter
    public void update(WorkoutDAO workout) {
        exercisesListAdapter = new ExerciseListAdapter(context, workout.getExercises());
        exercises.setAdapter(exercisesListAdapter);

        setAuthor(workout.getAuthor());
        setTitle(workout.getTitle());
        setRepetitions(workout.getRepetitions());

        // Changes the height to be big enough to contain the workout
        ViewGroup.LayoutParams params = exercises.getLayoutParams();
        int HEIGHT_PER_ENTRY = 68;
        int height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                HEIGHT_PER_ENTRY * workout.getExercises().size(),
                getResources().getDisplayMetrics());
        params.height = height;
        exercises.setLayoutParams(params);
    }

    private void initComponents() {
        titleText = (TextView) findViewById(R.id.titleText);
        authorText = (TextView) findViewById(R.id.authorText);
        exercises = (RecyclerView) findViewById(R.id.exercisesList);
        repetitionsText = (TextView) findViewById(R.id.repetitionsText);

        exercises.setNestedScrollingEnabled(false);
        initExercisesList();
    }

    private void initExercisesList() {
        exercisesLayoutManager = new LinearLayoutManager(context);
        exercises.setLayoutManager(exercisesLayoutManager);

        ArrayList<Exercise> exerciseFakeList = new ArrayList<>();
        exerciseFakeList.add(new Exercise("Crunches", false, "5"));

        exercisesListAdapter = new ExerciseListAdapter(context, exerciseFakeList);
        exercises.setAdapter(exercisesListAdapter);

    }


    private void setRepetitions(CharSequence repetitions) {
        repetitionsText.setText(repetitions + " repetitions");
    }

    private void setTitle(CharSequence title) {
        titleText.setText(title);
    }

    private void setAuthor(CharSequence author) {
        authorText.setText(author);
    }

}
