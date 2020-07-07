package com.liversedge.workoutselector.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.backend.db.databases.AppDatabase;
import com.liversedge.workoutselector.backend.db.entities.Exercise;
import com.liversedge.workoutselector.backend.db.entities.ExerciseGroupTable;
import com.liversedge.workoutselector.backend.db.entities.WorkoutTable;
import com.liversedge.workoutselector.backend.db.migrations.Migrations;
import com.liversedge.workoutselector.frontend.adapters.ExerciseItem;
import com.liversedge.workoutselector.frontend.videos.YoutubeVideoFragment;
import com.liversedge.workoutselector.utils.ExerciseImageIds;
import com.liversedge.workoutselector.utils.ImageHelper;

import static com.liversedge.workoutselector.utils.Constants.INTENT_EXERCISE_ID;
import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID;
import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID_IS_PRESENT;

public class ExerciseActivity extends AppCompatActivity {

    // Youtube video set elements
    private YouTubePlayerView exerciseVideoView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    // SQL related
    private AppDatabase localDB;
    private Exercise exercise;
    private ExerciseGroupTable exerciseGroup;
    private WorkoutTable workout;
    private ExerciseItem exerciseItem;

    // Exercise to image and video id helper
    ExerciseImageIds exerciseImageIds;

    // Top app bar
    MaterialToolbar topAppBar;

    // Workout ID
    private int workoutID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Gets an instance of the database
        localDB = Migrations.getInstance(this).db;

        // Gets the exercise information
        Intent intent = getIntent();
        Integer exerciseID = intent.getIntExtra(INTENT_EXERCISE_ID, -1);
        exercise = localDB.appDao().getExerciseByID(exerciseID).get(0);
        exerciseGroup = localDB.appDao().getExerciseGroupsByGroupID(exercise.groupID).get(0);
        workout = localDB.appDao().getWorkoutByID(exercise.workoutID).get(0);

        // Get the workout id
        workoutID = intent.getIntExtra(INTENT_WORKOUT_ID, -1);

        // Get the video ID
        exerciseImageIds = new ExerciseImageIds();
        final String videoID = exerciseImageIds.getVideoByExerciseName(exercise.name);
        exerciseItem = new ExerciseItem(
                exercise.name,
                String.valueOf(exercise.duration),
                exercise.isTimed,
                exercise.hasEnding,
                exercise.ending,
                exercise.id
        );

        // Set up the top navigation bar
        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to home again
                Intent toHome = new Intent(ExerciseActivity.this, MainActivity.class);
                toHome.putExtra(INTENT_WORKOUT_ID, workoutID);
                toHome.putExtra(INTENT_WORKOUT_ID_IS_PRESENT, true);
                startActivity(toHome);
            }
        });

        // Capitalize first letter + change title
        String text = exercise.name.toLowerCase();
        String cap = text.substring(0, 1).toUpperCase() + text.substring(1);
        topAppBar.setTitle(cap);

        // Create the video
        // Create Youtube Fragment instance by passing a Youtube Video ID
        YoutubeVideoFragment youtubeFragment =
                YoutubeVideoFragment.newInstance(videoID);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.exerciseInstructionVideo, youtubeFragment).commit();
    }

}
