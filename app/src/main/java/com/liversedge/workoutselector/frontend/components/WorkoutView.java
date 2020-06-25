package com.liversedge.workoutselector.frontend.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.backend.firebase.ExerciseDAO;
import com.liversedge.workoutselector.backend.firebase.ExerciseGroupDAO;
import com.liversedge.workoutselector.backend.firebase.WorkoutDAO;
import com.liversedge.workoutselector.frontend.adapters.DescriptionItem;
import com.liversedge.workoutselector.frontend.adapters.ExerciseItem;
import com.liversedge.workoutselector.frontend.adapters.ExerciseListAdapter;
import com.liversedge.workoutselector.frontend.adapters.WorkoutElement;

import java.util.ArrayList;
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
        CharSequence repetitions = typedArray.getText(R.styleable.WorkoutView_repetitionsText);

        typedArray.recycle();

        initComponents();

        setAuthor(author);
        setTitle(title);
    }

    // Updates the adapter
    public void update(WorkoutDAO workout) {

        // Make the list with the size
        List<ExerciseGroupDAO> groups = workout.getExerciseGroups();
        ArrayList<WorkoutElement> displayItems = new ArrayList<>();

        for(int i=0; i<groups.size(); i++) {
            // Grab the current group
            ExerciseGroupDAO group = groups.get(i);
            // Add a title item
            displayItems.add(
                new DescriptionItem(
                    i+1,
                    group.getDescription()
                )
            );

            // Add all the actual exercise items
            List<ExerciseDAO> exercises = group.getExercises();
            for(int j=0; j<exercises.size(); j++) {
                ExerciseDAO exercise = exercises.get(j);

                // The case where there is no duration given
                Integer duration = exercise.getDuration();
                String durationString = duration < 0 ? "" : String.valueOf(duration);

                displayItems.add(
                    new ExerciseItem(
                        exercise.getName(),
                        durationString,
                        exercise.isTimed(),
                        exercise.isHasEnding(),
                        exercise.getEnding()
                    )
                );
            }
        }

        exercisesListAdapter = new ExerciseListAdapter(context, displayItems);
        exercises.setAdapter(exercisesListAdapter);

        setAuthor(workout.getAuthor());
        setTitle(workout.getTitle());

        // Changes the height to be big enough to contain the workout
        ViewGroup.LayoutParams params = exercises.getLayoutParams();
        int HEIGHT_PER_ENTRY = 68;
        int height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                HEIGHT_PER_ENTRY * displayItems.size(),
                getResources().getDisplayMetrics());
        params.height = height;
        exercises.setLayoutParams(params);
    }

    private void initComponents() {
        titleText = (TextView) findViewById(R.id.titleText);
        authorText = (TextView) findViewById(R.id.authorText);
        exercises = (RecyclerView) findViewById(R.id.exercisesList);

        exercises.setNestedScrollingEnabled(false);
        initExercisesList();
    }

    private void initExercisesList() {
        exercisesLayoutManager = new LinearLayoutManager(context);
        exercises.setLayoutManager(exercisesLayoutManager);

        ArrayList<WorkoutElement> exerciseFakeList = new ArrayList<>();
        exerciseFakeList.add(new ExerciseItem("Crunches", "5", false, false, "N/A"));

        exercisesListAdapter = new ExerciseListAdapter(context, exerciseFakeList);
        exercises.setAdapter(exercisesListAdapter);

    }

    private void setTitle(CharSequence title) {
        titleText.setText(title);
    }

    private void setAuthor(CharSequence author) {
        if(author != null) {
            if (!author.toString().equals("")) {
                authorText.setText("By " + author);
                exercises.setVisibility(View.VISIBLE);
            } else {
//                authorText.setVisibility(View.GONE);
                exercises.setVisibility(View.GONE);
//                LayoutParams params = new LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.WRAP_CONTENT
//                );
//
//                Resources r = context.getResources();
//                int margin = (int) TypedValue.applyDimension(
//                        TypedValue.COMPLEX_UNIT_DIP,
//                        16,
//                        r.getDisplayMetrics()
//                );
//
//                params.setMargins(margin, margin, 0, margin);
//                titleText.setLayoutParams(params);
                authorText.setText("");
            }
        }
    }

}
