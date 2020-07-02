package com.liversedge.workoutselector.frontend.components;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.liversedge.workoutselector.utils.ExerciseImageIds;

import java.util.ArrayList;
import java.util.List;

public class WorkoutView extends ConstraintLayout {

    // Text components
    private TextView authorText;
    private ImageView authorImage;

    // List of exercises
    private RecyclerView exercises;
    private LinearLayoutManager exercisesLayoutManager;
    private ExerciseListAdapter exercisesListAdapter;
    private ConstraintLayout authorLabel;

    // Saved for updates
    private Context context;
    private ExerciseImageIds exerciseImageIds;

    public WorkoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        exerciseImageIds = new ExerciseImageIds();
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        this.context = context;

        inflate(context, R.layout.workout_description, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WorkoutView);

        CharSequence author = typedArray.getText(R.styleable.WorkoutView_authorText);
        CharSequence repetitions = typedArray.getText(R.styleable.WorkoutView_repetitionsText);

        typedArray.recycle();

        initComponents();

        setAuthor(author);
    }

    // Updates the adapter
    public void update(final WorkoutDAO workout) {

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
                        exercise.getEnding(),
                        exercise.getId()
                    )
                );
            }
        }

        // Set up the exercises
        exercisesListAdapter = new ExerciseListAdapter(context, displayItems);
        exercises.setAdapter(exercisesListAdapter);

        // Change the author name
        setAuthor(workout.getAuthor());

        // Open instagram page on click
        authorLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(workout.getAuthor_url());
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    context.startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/xxx")));
                }
            }
        });

        // Changes the height to be big enough to contain the workout
//        ViewGroup.LayoutParams params = exercises.getLayoutParams();
//        int HEIGHT_PER_ENTRY = 68;
//        int height = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                HEIGHT_PER_ENTRY * displayItems.size(),
//                getResources().getDisplayMetrics());
//        params.height = height;
//        exercises.setLayoutParams(params);
    }

    private void initComponents() {
        authorText = (TextView) findViewById(R.id.authorText);
        exercises = (RecyclerView) findViewById(R.id.exercisesList);
        authorLabel = (ConstraintLayout) findViewById(R.id.descriptionTitle);
        authorImage = (ImageView) findViewById(R.id.authorImage);

//        exercises.setNestedScrollingEnabled(false);
        initExercisesList();
    }

    private void initExercisesList() {
        exercisesLayoutManager = new LinearLayoutManager(context);
        exercises.setLayoutManager(exercisesLayoutManager);

        ArrayList<WorkoutElement> exerciseFakeList = new ArrayList<>();
        exerciseFakeList.add(new ExerciseItem("Crunches", "5", false, false, "N/A", -1));

        exercisesListAdapter = new ExerciseListAdapter(context, exerciseFakeList);
        exercises.setAdapter(exercisesListAdapter);

    }

    private void setAuthor(CharSequence author) {
        if(author != null) {
            if (!author.toString().equals("")) {
                authorText.setText(author);
                exercises.setVisibility(View.VISIBLE);
                authorImage.setImageDrawable(context.getResources().getDrawable(exerciseImageIds.getAuthorImage(author.toString())));
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
